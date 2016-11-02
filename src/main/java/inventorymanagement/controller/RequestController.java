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
import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;
import inventorymanagement.service.RequestServiceInterface;

@RestController
public class RequestController {

  /**
   * {@link Logger}
   */
  private static final Logger LOG = Logger.getLogger(RequestController.class);

  /**
   * {@link RequestServiceInterface}
   */
  @Autowired
  RequestServiceInterface requestService;

  /**
   * Controller method to raise a new request
   * 
   * @param request {@link IncomingRequestModel} contains User Id and Product Id required for
   *        raising the request
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/requests", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public RequestModel create(@RequestBody IncomingRequestModel request) throws BadRequestException {
    LOG.info("Request received for raising a request");
    RequestModel requestModel = requestService.addRequest(request);
    LOG.info("Request for raising a request successful");
    return requestModel;
  }

  /**
   * Controller method to update a request with a reply
   * 
   * @param id {@link Integer} Id of request to be updated
   * @param request {@link IncomingUpdateRequest} contains reply to be updated for a user.
   * @param session {@link HttpSession}
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws NotFoundException Thrown when no request with given id is found
   */
  @RequestMapping(value = "/requests/{id}", method = RequestMethod.PATCH)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public RequestModel update(@PathVariable int id, @RequestBody IncomingUpdateRequest request,
      HttpSession session) throws BadRequestException, UnauthorizedException, NotFoundException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received for updating request");
    RequestModel requestModel = requestService.updateRequest(id, request);
    LOG.info("Request for updating a request successful");
    return requestModel;
  }

  /**
   * Controller method to get all requests from the database
   * 
   * @return {@link RequestModel} List of all Requests mapped to RequestModel
   */
  @RequestMapping(value = "/requests", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<RequestModel> getAll() {
    LOG.info("Request received for getting all requests");
    List<RequestModel> requests = requestService.getAllRequests();
    LOG.info("Request for getting all requests successful");
    return requests;
  }

  /**
   * Controller method to get Request details by id
   * 
   * @param id {@link Integer} Id of request
   * @return {@link RequestModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no request with given id is found
   */
  @RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public RequestModel getById(@PathVariable int id) throws BadRequestException, NotFoundException {
    LOG.info("Request received for getting a request by id");
    RequestModel request = requestService.getRequestById(id);
    LOG.info("Request for getting a request by id successful");
    return request;
  }

  /**
   * Controller method to get Request details by user id
   * 
   * @param id {@link Integer} id of user
   * @return {@link RequestModel} List of RequestModel
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/requests/user/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<RequestModel> getByUserId(@PathVariable int id) throws BadRequestException {
    LOG.info("Request received for getting a request by id");
    List<RequestModel> request = requestService.getRequestByUserId(id);
    LOG.info("Request for getting a request by id successful");
    return request;
  }

}
