package inventorymanagement.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import inventorymanagement.entities.Product;
import inventorymanagement.entities.Request;
import inventorymanagement.entities.User;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;

@Service
public class RequestServiceUtils {

  public List<RequestModel> mapRequestsToModel(List<Request> requests) {
    List<RequestModel> requestModels = new ArrayList<>();

    for (Request request : requests) {
      requestModels.add(mapRequest(request));
    }
    return requestModels;
  }

  public RequestModel mapRequest(Request request) {
    RequestModel requestModel = new RequestModel();
    requestModel.setReply(request.getReply());
    requestModel.setId(request.getId());
    requestModel.setStatus(request.isStatus());
    User user = request.getUser();
    Product product = request.getProduct();
    requestModel.setUserName(user.getName());
    requestModel.setProductCompany(product.getCompany());
    requestModel.setVersion(product.getVersion());
    requestModel.setProductName(product.getName());
    return requestModel;
  }

  public void mapFromUpdateRequest(IncomingUpdateRequest requestModel, Request request) {
    String reply = requestModel.getReply();
    request.setReply(reply);
  }
}
