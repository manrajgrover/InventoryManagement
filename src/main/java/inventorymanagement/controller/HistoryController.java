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
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.model.InventoryModel;
import inventorymanagement.service.HistoryServiceInterface;

/**
 * Class HistoryController is a controller that handle requests for issuing and returning of items.
 * 
 * @author manrajsingh
 *
 */
@RestController
public class HistoryController {

  /**
   * {@link Logger}
   */
  private static final Logger LOG = Logger.getLogger(RequestController.class);

  /**
   * {@link HistoryServiceInterface}
   */
  @Autowired
  HistoryServiceInterface historyService;

  /**
   * Controller method to issue an item to a user
   * 
   * @param historyModel {@link IncomingHistoryModel} contains product tag, user id and product id
   *        required for issuing item to user
   * @param session {@link HttpSession} for validating if user is admin or not
   * @return {@link HistoryModel}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no product or item with id or tag is found
   */
  @RequestMapping(value = "/history", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public HistoryModel create(@RequestBody IncomingHistoryModel historyModel, HttpSession session)
      throws UnauthorizedException, BadRequestException, NotFoundException {
    LOG.info("Request received to issue an item");

    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);

    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }

    return historyService.issueItem(historyModel);
  }

  /**
   * Controller method to take item returns from a user
   * 
   * @param id {@link Integer} Issue ticket number for the issued item
   * @param historyModel {@link IncomingReturnModel} contains product tag id of the item being
   *        returned
   * @param session {@link HttpSession} for validating if user is admin or not
   * @return {@link HistoryModel}
   * @throws NotFoundException Thrown when no product or item with id or tag is found
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/history/{id}", method = RequestMethod.PATCH)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public HistoryModel update(@PathVariable int id, @RequestBody IncomingReturnModel historyModel,
      HttpSession session) throws NotFoundException, UnauthorizedException, BadRequestException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);

    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }

    return historyService.returnItem(id, historyModel);
  }
  
  /**
   * Controller method to get inventory of particular user
   * 
   * @param id {@link Integer} ID of user
   * @return {@link InventoryModel} List of Inventory owned by user
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/history/user/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryModel> getByUserId(@PathVariable int id) throws BadRequestException {
    return historyService.getHistoryByUserId(id);
  }
}
