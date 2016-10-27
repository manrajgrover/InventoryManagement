package inventorymanagement.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.constants.Constants;
import inventorymanagement.dao.HistoryDaoInterface;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.dao.UserDaoInterface;
import inventorymanagement.entities.History;
import inventorymanagement.entities.Item;
import inventorymanagement.entities.Product;
import inventorymanagement.entities.User;
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.model.OutgoingHistoryModel;
import inventorymanagement.utilities.HistoryServiceUtils;

@Service
public class HistoryServiceImpl implements HistoryServiceInterface {

  @Autowired
  HistoryDaoInterface historyDaoImpl;

  @Autowired
  UserDaoInterface userDaoImpl;

  @Autowired
  ItemDaoInterface itemDaoImpl;

  @Autowired
  HistoryServiceUtils historyServiceUtils;

  @Override
  @Transactional
  public HistoryModel issueItem(IncomingHistoryModel historyModel) {
    OutgoingHistoryModel avail = historyServiceUtils.checkAvailability(historyModel);
    HistoryModel hm = new HistoryModel();
    if (!avail.getAvailability()) {
      hm.setAvailability(false);
      hm.setMessage(Constants.ITEM_NOT_AVAILABLE);
      return hm;
    } else {
      int userId = historyModel.getUserId();
      User user = userDaoImpl.getById(userId);

      String name = user.getName();
      int productId = historyModel.getProductId();
      Product product = new Product(productId);

      int itemId = avail.getId();
      Item item = itemDaoImpl.getById(itemId);
      item.setAvailable("No");
      itemDaoImpl.update(item);

      History history = new History(user, product, item, name);
      historyDaoImpl.save(history);
      hm.setId(history.getId());
      hm.setAvailability(true);
      hm.setMessage(Constants.ITEM_AVAILABLE_ISSUED);
      return hm;
    }
  }

  @Override
  @Transactional
  public HistoryModel returnItem(int issueNumber, IncomingReturnModel historyModel)
      throws NotFoundException {

    OutgoingHistoryModel avail = historyServiceUtils.checkIfExist(historyModel);

    HistoryModel hm = new HistoryModel();

    if (avail.getAvailability()) {
      hm.setAvailability(true);
      hm.setMessage(Constants.ITEM_ALREADY_RETURNED);
      return hm;
    } else {
      History history = historyDaoImpl.getById(issueNumber);
      Item itemByIssueNumber = history.getItem();

      String itemTag = historyModel.getProductTag();

      Item itemByTag = itemDaoImpl.getByItemTag(itemTag);

      if (itemByIssueNumber.getId() == itemByTag.getId()) {
        history.setReturnTimestamp(new Date());
        historyDaoImpl.update(history);
        itemByTag.setAvailable("Yes");
        itemDaoImpl.update(itemByTag);
        hm.setAvailability(true);
        hm.setMessage(Constants.ITEM_RETURNED);
        return hm;
      } else {
        hm.setMessage(Constants.ITEM_MISMATCH);
        return hm;
      }
    }
  }
}
