package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.constants.Constants;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.entities.Item;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.utilities.ItemServiceUtils;

/**
 * @see ItemServiceInterface
 * @author manrajsingh
 *
 */
@Service
public class ItemServiceImpl implements ItemServiceInterface {
  
  /**
   * {@link ItemDaoInterface}
   */
  @Autowired
  ItemDaoInterface itemDaoImpl;

  /**
   * {@link ItemServiceUtils}
   */
  @Autowired
  ItemServiceUtils itemServiceUtils;

  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#addItem(inventorymanagement.model.IncomingItemModel)
   */
  @Override
  @Transactional
  public ItemModel addItem(IncomingItemModel itemModel) throws BadRequestException {
    
    if (itemModel.getProductTag() == null || itemModel.getProductTag().equals("") || itemModel.getProductId() <= 0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }

    Product product = new Product(itemModel.getProductId());
    String tag = itemModel.getProductTag();
    String available = "Yes";

    Item item = new Item(product, tag, available);
    itemDaoImpl.save(item);
    itemDaoImpl.refresh(item);

    ItemModel im = new ItemModel(item, tag, Constants.ITEM_CREATED_MESSAGE);
    return im;
  }
  
  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#updateItem(int, inventorymanagement.model.IncomingItemModel)
   */
  @Override
  @Transactional
  public ItemModel updateItem(int id, IncomingItemModel itemModel) throws BadRequestException, NotFoundException {
    
    if (itemModel.getProductTag() == null || itemModel.getProductTag().equals("") || itemModel.getProductId() <= 0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    
    try {
      Item item = itemDaoImpl.getById(id);

      item = itemServiceUtils.mapFromUpdateItem(itemModel, item);
      itemDaoImpl.update(item);
      itemDaoImpl.flush();
      itemDaoImpl.refresh(item);
      ItemModel im = new ItemModel(item, Constants.ITEM_UPDATED_MESSAGE);
      return im;
    } catch(Exception e) {
      throw new NotFoundException("Item with given ID does not exist");
    }
  }
  
  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#deleteItem(int)
   */
  @Override
  @Transactional
  public void deleteItem(int id) throws BadRequestException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    Item item = new Item(id);
    itemDaoImpl.delete(item);
  }
  
  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#getAllItems()
   */
  @Override
  @Transactional
  public List<ItemModel> getAllItems() {
    List<Item> itemsList = itemDaoImpl.getAll();
    return itemServiceUtils.mapItemsToModel(itemsList);
  }

  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#getItemById(int)
   */
  @Override
  @Transactional
  public ItemModel getItemById(int id) throws BadRequestException, NotFoundException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    try {
      Item item = itemDaoImpl.getById(id);
      return itemServiceUtils.mapItemToModel(item);
    }
    catch(Exception e) {
      throw new NotFoundException("Item with given ID does not exist");
    }
  }

  /*
   * (non-Javadoc)
   * @see inventorymanagement.service.ItemServiceInterface#getCountItem(int)
   */
  @Override
  @Transactional
  public int getCountItem(int id) throws BadRequestException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    return itemDaoImpl.getCountByProductId(id);
  }
  
  
}
