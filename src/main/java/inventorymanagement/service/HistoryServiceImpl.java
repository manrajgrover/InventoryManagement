package inventorymanagement.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
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

/**
 * @see HistoryServiceInterface
 * @author manrajsingh
 *
 */
@Service
public class HistoryServiceImpl implements HistoryServiceInterface {
  
  /**
   * {@link HistoryDaoInterface}
   */
  @Autowired
  HistoryDaoInterface historyDaoImpl;

  /**
   * {@link UserDaoInterface}
   */
  @Autowired
  UserDaoInterface userDaoImpl;
  
  /**
   * {@link ItemDaoInterface}
   */
  @Autowired
  ItemDaoInterface itemDaoImpl;
  
  /**
   * {@link HistoryServiceUtils}
   */
  @Autowired
  HistoryServiceUtils historyServiceUtils;

  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.HistoryServiceInterface#issueItem(inventorymanagement.model.IncomingHistoryModel)
   */
  @Override
  @Transactional
  public HistoryModel issueItem(IncomingHistoryModel historyModel) throws BadRequestException, NotFoundException {

    if (historyModel.getProductTag() == null || historyModel.getProductTag().equals("") || historyModel.getProductId() <= 0
        || historyModel.getUserId() <= 0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }

    OutgoingHistoryModel avail = historyServiceUtils.checkAvailability(historyModel);
    HistoryModel hm = new HistoryModel();
    if (!avail.getAvailability()) {
      hm.setAvailability(false);
      hm.setMessage(Constants.ITEM_NOT_AVAILABLE);
      return hm;
    } else {
      int userId = historyModel.getUserId();
      User user = userDaoImpl.getById(userId);

      int productId = historyModel.getProductId();
      Product product = new Product(productId);

      int itemId = avail.getId();
      Item item = itemDaoImpl.getById(itemId);
      item.setAvailable("No");
      itemDaoImpl.update(item);

      History history = new History(user, product, item);
      historyDaoImpl.save(history);
      hm.setId(history.getId());
      hm.setAvailability(true);
      hm.setMessage(Constants.ITEM_AVAILABLE_ISSUED);
      return hm;
    }
  }

  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.HistoryServiceInterface#returnItem(int, inventorymanagement.model.IncomingReturnModel)
   */
  @Override
  @Transactional
  public HistoryModel returnItem(int issueNumber, IncomingReturnModel historyModel)
      throws NotFoundException, BadRequestException {
    
    if (issueNumber <=0 || historyModel.getProductTag() == null || historyModel.getProductTag().equals("")) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    
    OutgoingHistoryModel avail = historyServiceUtils.checkIfExist(historyModel);

    HistoryModel hm = new HistoryModel();

    if (avail.getAvailability()) {
      hm.setAvailability(true);
      hm.setMessage(Constants.ITEM_ALREADY_RETURNED);
      return hm;
    } else {
      
      try {
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
        } else {
          hm.setMessage(Constants.ITEM_MISMATCH);
        }
        return hm;
      } catch (Exception e) {
        throw new NotFoundException("Issued Ticket does not reference to a record");
      }
      
    }
  }
}
