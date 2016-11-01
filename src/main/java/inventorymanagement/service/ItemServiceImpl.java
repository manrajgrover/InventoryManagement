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

@Service
public class ItemServiceImpl implements ItemServiceInterface {

  @Autowired
  ItemDaoInterface itemDaoImpl;

  @Autowired
  ItemServiceUtils itemServiceUtils;

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

  @Override
  @Transactional
  public void deleteItem(int id) throws BadRequestException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    Item item = new Item(id);
    itemDaoImpl.delete(item);
  }

  @Override
  @Transactional
  public List<ItemModel> getAllItems() {
    List<Item> itemsList = itemDaoImpl.getAll();
    return itemServiceUtils.mapItemsToModel(itemsList);
  }

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

  @Override
  @Transactional
  public int getCountItem(int id) throws BadRequestException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    return itemDaoImpl.getCountByProductId(id);
  }
  
  
}
