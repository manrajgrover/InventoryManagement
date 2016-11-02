package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;

public interface RequestServiceInterface {
  public RequestModel addRequest(IncomingRequestModel requestModel) throws BadRequestException;

  public List<RequestModel> getAllRequests();

  RequestModel getRequestById(int id) throws BadRequestException, NotFoundException;

  public RequestModel updateRequest(int id, IncomingUpdateRequest request) throws BadRequestException, NotFoundException;

  public List<RequestModel> getRequestByUserId(int id) throws BadRequestException;
}
