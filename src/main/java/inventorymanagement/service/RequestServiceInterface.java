package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;

/**
 * Service to raise requests, update replies, and get details of the same
 * 
 * @author manrajsingh
 *
 */
public interface RequestServiceInterface {

  /**
   * Method to add a request to database
   * 
   * @param requestModel {@link IncomingRequestModel} contains details required for raising a
   *        request
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   */
  public RequestModel addRequest(IncomingRequestModel requestModel) throws BadRequestException;

  /**
   * Method to get all Requests
   * 
   * @return List of Requests mapped to RequestModel
   */
  public List<RequestModel> getAllRequests();

  /**
   * Method to get details of request by id
   * 
   * @param id {@link Integer} Id of Request
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when request with given id does not exist in database
   */
  RequestModel getRequestById(int id) throws BadRequestException, NotFoundException;

  /**
   * Method to update a request's reply
   * 
   * @param id {@link Integer} Id of Request
   * @param request {@link IncomingUpdateRequest} contains details required to update a request
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when request with given id does not exist in database
   */
  public RequestModel updateRequest(int id, IncomingUpdateRequest request)
      throws BadRequestException, NotFoundException;

  /**
   * Method to get all requests made by a user
   * 
   * @param id {@link Integer} Id of user
   * @return {@link RequestModel} List of Requests mapped to RequestModel
   * @throws BadRequestException Thrown when any of the field is empty
   */
  public List<RequestModel> getRequestByUserId(int id) throws BadRequestException;
}
