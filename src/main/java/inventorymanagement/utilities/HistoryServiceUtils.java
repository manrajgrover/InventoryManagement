package inventorymanagement.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.controller.RequestController;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.entities.History;
import inventorymanagement.entities.Item;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.model.InventoryModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.model.OutgoingHistoryModel;

@Service
public class HistoryServiceUtils {

  private static final Logger LOG = Logger.getLogger(RequestController.class);

  @Autowired
  ItemDaoInterface itemDaoImpl;

  public OutgoingHistoryModel checkAvailability(IncomingHistoryModel historyModel)
      throws NotFoundException {
    try {
      LOG.info("Checking if item exists");
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
    } catch (Exception e) {
      throw new NotFoundException("Item not found");
    }
  }

  public OutgoingHistoryModel checkIfExist(IncomingReturnModel historyModel)
      throws NotFoundException {
    try {
      Item item = itemDaoImpl.getByItemTag(historyModel.getProductTag());

      OutgoingHistoryModel outgoingModel = new OutgoingHistoryModel();
      if (item.getAvailable().equals("Yes")) {
        outgoingModel.setAvailability(true);
      } else if (item.getAvailable().equals("No")) {
        outgoingModel.setAvailability(false);
      }
      return outgoingModel;
    } catch (Exception e) {
      throw new NotFoundException("Item not found");
    }
  }

  public List<InventoryModel> mapHistoryToInventory(List<History> history) {
    List<InventoryModel> historyModel = new ArrayList<>();

    for (History htry: history) {
      historyModel.add(mapHistoryToModel(htry));
    }

    return historyModel;
  }

  private InventoryModel mapHistoryToModel(History htry) {
    InventoryModel inv = new InventoryModel();
    Product product = htry.getProduct();
    inv.setProductCompany(product.getCompany());
    inv.setProductName(product.getName());
    inv.setProductVersion(product.getVersion());
    inv.setId(htry.getId());
    return inv;
  }
  
}
