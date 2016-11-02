package inventorymanagement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inti.ws.spring.exception.client.UnauthorizedException;
import inventorymanagement.constants.Constants;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.service.ItemServiceInterface;

/**
 * Class ItemController is a controller that handles requests for adding, updating, deleting as well
 * as getting information of particular item as well as for all items. It also handles requests for
 * getting count of particular item type.
 * 
 * @author manrajsingh
 *
 */
@RestController
public class ItemController {

  /**
   * {@link Logger}
   */
  private static final Logger LOG = Logger.getLogger(ItemController.class);

  /**
   * {@link ItemServiceInterfae}
   */
  @Autowired
  ItemServiceInterface itemService;

  /**
   * Controller method to add new items
   * 
   * @param itemModel {@link IncomingItemModel} contains Product Id and Product Tag required for
   *        creating a new item
   * @param session {@link HttpSession} for validating if user is admin or not
   * @return {@link ItemModel}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/items", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public ItemModel create(@RequestBody IncomingItemModel itemModel, HttpSession session)
      throws UnauthorizedException, BadRequestException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received to add an item");
    ItemModel item = itemService.addItem(itemModel);
    LOG.info("Request to add an item successful");
    return item;
  }

  /**
   * Controller method to update details of an item
   * 
   * @param id {@link Integer} Item Id of the item that needs to be updated
   * @param itemModel {@link IncomingItemModel} contains Product Id and Product Tag required for
   *        updating an item
   * @param session {@link HttpSession} for validating if user is admin or not
   * @return {@link ItemModel}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no product or item with id or tag is found
   */
  @RequestMapping(value = "/items/{id}", method = RequestMethod.PATCH)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ItemModel update(@PathVariable int id, @RequestBody IncomingItemModel itemModel,
      HttpSession session) throws UnauthorizedException, BadRequestException, NotFoundException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received to update an item");
    ItemModel item = itemService.updateItem(id, itemModel);
    LOG.info("Request to update an item successful");
    return item;
  }

  /**
   * Controller method to get count of items in the database that are available
   * 
   * @param id {@link Integer} Product ID whose count is needed
   * @return {@link Integer} Number of items with given Product ID and are available
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/items/{id}/count", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public int getCount(@PathVariable int id) throws BadRequestException {
    int count = itemService.getCountItem(id);
    return count;
  }

  /**
   * Controller method to delete an item from the database
   * 
   * @param id {@link Integer} Item Id required for deletion
   * @param session {@link HttpSession} for validating if user is admin or not
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable int id, HttpSession session)
      throws UnauthorizedException, BadRequestException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received to delete an item");
    itemService.deleteItem(id);
    LOG.info("Request to delete an item successful");
  }

  /**
   * Controller method to get all items from the database
   * 
   * @return {@link ItemModel} List of all Items mapped to ItemModel
   */
  @RequestMapping(value = "/items", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<ItemModel> getAll() {
    LOG.info("Request received to get all items");
    List<ItemModel> itemModels = itemService.getAllItems();
    LOG.info("Request to get all items successful");
    return itemModels;
  }

  /**
   * Controller method to get details of particular item
   * 
   * @param id {@link Integer} Item Id required for getting details
   * @return {@link ItemModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no item with id is found
   */
  @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ItemModel getById(@PathVariable int id) throws BadRequestException, NotFoundException {
    LOG.info("Request received to get an item by id");
    ItemModel item = itemService.getItemById(id);
    LOG.info("Request to get an item by id successful");
    return item;
  }
}
