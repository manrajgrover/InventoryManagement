package inventorymanagement.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.entities.Item;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.model.OutgoingHistoryModel;

@Service
public class HistoryServiceUtils {

  @Autowired
  ItemDaoInterface itemDaoImpl;

  public OutgoingHistoryModel checkAvailability(IncomingHistoryModel historyModel) {
    Item item = itemDaoImpl.getByItemTag(historyModel.getProductTag());
    OutgoingHistoryModel outgoingModel = new OutgoingHistoryModel();
    Product productOfItem = item.getProduct();
    int idOfProductOfItem = productOfItem.getId();

    if (item == null || item.getAvailable().equals("No")
        || historyModel.getProductId() != idOfProductOfItem) {
      outgoingModel.setAvailability(false);
      return outgoingModel;
    }
    outgoingModel.setAvailability(true);
    outgoingModel.setId(item.getId());
    return outgoingModel;
  }

  public OutgoingHistoryModel checkIfExist(IncomingReturnModel historyModel)
      throws NotFoundException {
    Item item = itemDaoImpl.getByItemTag(historyModel.getProductTag());

    OutgoingHistoryModel outgoingModel = new OutgoingHistoryModel();

    if (item != null && item.getAvailable().equals("Yes")) {
      outgoingModel.setAvailability(true);
      return outgoingModel;
    } else if (item != null && item.getAvailable().equals("No")) {
      outgoingModel.setAvailability(false);
      return outgoingModel;
    } else {
      throw new NotFoundException("Item not found");
    }
  }

}
