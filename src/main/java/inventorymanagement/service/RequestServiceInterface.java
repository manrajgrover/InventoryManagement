package inventorymanagement.service;

import java.util.List;

import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;

public interface RequestServiceInterface {
  public RequestModel addRequest(IncomingRequestModel requestModel);

  public List<RequestModel> getAllRequests();

  RequestModel getRequestById(int id);

  public RequestModel updateRequest(int id, IncomingUpdateRequest request);
}
