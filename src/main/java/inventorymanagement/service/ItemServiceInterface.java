package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;

/**
 * Service that handles requests related to items, including adding, updating, deleting and getting
 * details
 * 
 * @author manrajsingh
 *
 */
public interface ItemServiceInterface {

  /**
   * Method to add item to the database
   * 
   * @param productModel {@link IncomingItemModel} contains details needed to add a new item
   * @return {@link ItemModel}
   * @throws BadRequestException Thrown when any of the field required is empty
   */
  public ItemModel addItem(IncomingItemModel productModel) throws BadRequestException;

  /**
   * Method to update item in the database
   * 
   * @param id {@link Integer} Id of item
   * @param productModel {@link IncomingItemModel} contains updated fields for the item
   * @return {@link ItemModel}
   * @throws BadRequestException Thrown when any of the field required is empty
   * @throws NotFoundException Thrown when item with given id does not exist in database
   */
  public ItemModel updateItem(int id, IncomingItemModel productModel)
      throws BadRequestException, NotFoundException;

  /**
   * Method to delete an item from the database
   * 
   * @param id {@link Integer} Id of item
   * @throws BadRequestException Thrown when any of the field required is empty
   */
  public void deleteItem(int id) throws BadRequestException;

  /**
   * Method to get all items from the database
   * 
   * @return {@link ItemModel} List of items mapped to ItemModel
   */
  public List<ItemModel> getAllItems();

  /**
   * Method to get item by id from the database
   * 
   * @param id {@link Integer} Id of item
   * @return {@link ItemModel}
   * @throws BadRequestException Thrown when any of the field required is empty
   * @throws NotFoundException Thrown when item with given id does not exist in database
   */
  public ItemModel getItemById(int id) throws BadRequestException, NotFoundException;

  /**
   * Method to get count of item with given product id
   * 
   * @param id {@link Integer} Id of Product
   * @return {@link Integer} Count of items with given product id
   * @throws BadRequestException Thrown when any of the field required is empty
   */
  public int getCountItem(int id) throws BadRequestException;
}
