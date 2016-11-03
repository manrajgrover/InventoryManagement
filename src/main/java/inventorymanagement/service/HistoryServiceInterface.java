package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.model.InventoryModel;

/**
 * Service that allows issuing of items and returning of the same
 * 
 * @author manrajsingh
 *
 */
public interface HistoryServiceInterface {

  /**
   * Method that handles requests to issue an item to a user
   * 
   * @param historyModel {@link IncomingHistoryModel} Model containing data needed for issuing item
   * @return {@link HistoryModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when item with particular tag or product id does not exist in
   *         database
   */
  HistoryModel issueItem(IncomingHistoryModel historyModel)
      throws BadRequestException, NotFoundException;

  /**
   * Method that handles requests to return an item
   * 
   * @param issueNumber {@link Integer} Issue Ticket number for particular item
   * @param historyModel {@link IncomingReturnModel} Model containing data needed for returning item
   * @return {@link HistoryModel}
   * @throws NotFoundException Thrown when item with particular tag or product id does not exist in
   *         database
   * @throws BadRequestException Thrown when any of the field is empty
   */
  HistoryModel returnItem(int issueNumber, IncomingReturnModel historyModel)
      throws NotFoundException, BadRequestException;

  /**
   * Method that handles requests to get inventory of a user
   * @param id {@link Integer} Id of user
   * @return {@link InventoryModel} List of inventory owned by user
   * @throws BadRequestException Thrown when any of the field is empty
   */
  List<InventoryModel> getHistoryByUserId(int id) throws BadRequestException;
}
