package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventorymanagement.constants.Constants;
import inventorymanagement.dao.RequestDaoInterface;
import inventorymanagement.entities.Product;
import inventorymanagement.entities.Request;
import inventorymanagement.entities.User;
import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;
import inventorymanagement.utilities.RequestServiceUtils;

@Service
public class RequestServiceImpl implements RequestServiceInterface {

  @Autowired
  RequestDaoInterface requestDaoImpl;

  @Autowired
  RequestServiceUtils requestUtils;

  @Override
  @Transactional
  public RequestModel addRequest(IncomingRequestModel requestModel) {
    User user = new User(requestModel.getUserId());
    Product product = new Product(requestModel.getProductId());
    String reply = "";
    boolean status = false;

    Request request = new Request(user, product, reply, status);
    requestDaoImpl.save(request);
    RequestModel rm = new RequestModel(request, Constants.REQUEST_CREATED_MESSAGE);
    return rm;
  }

  @Override
  @Transactional
  public List<RequestModel> getAllRequests() {
    List<Request> requests = requestDaoImpl.getAll();
    List<RequestModel> requestModel = requestUtils.mapRequestsToModel(requests);
    return requestModel;
  }

  @Override
  @Transactional
  public RequestModel getRequestById(int id) {
    Request request = requestDaoImpl.getById(id);
    RequestModel requestModel = requestUtils.mapRequest(request);
    return requestModel;
  }

  @Override
  @Transactional
  public RequestModel updateRequest(int id, IncomingUpdateRequest requestModel) {
    Request request = requestDaoImpl.getById(id);

    requestUtils.mapFromUpdateRequest(requestModel, request);
    requestDaoImpl.update(request);
    System.out.println(request.toString());
    RequestModel rm = new RequestModel(request, Constants.REQUEST_UPDATED_MESSAGE);
    return rm;
  }

}
