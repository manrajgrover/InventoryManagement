package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;

public interface ItemServiceInterface {

  public ItemModel addItem(IncomingItemModel productModel) throws BadRequestException;

  public ItemModel updateItem(int id, IncomingItemModel productModel) throws BadRequestException, NotFoundException;

  public void deleteItem(int id) throws BadRequestException;

  public List<ItemModel> getAllItems();

  public ItemModel getItemById(int id) throws BadRequestException, NotFoundException;

  public int getCountItem(int id) throws BadRequestException;
}
