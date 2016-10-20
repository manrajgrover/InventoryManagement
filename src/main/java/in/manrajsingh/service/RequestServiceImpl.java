package in.manrajsingh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manrajsingh.constants.Constants;
import in.manrajsingh.dao.RequestDaoInterface;
import in.manrajsingh.entities.Product;
import in.manrajsingh.entities.Request;
import in.manrajsingh.entities.User;
import in.manrajsingh.model.IncomingRequestModel;
import in.manrajsingh.model.RequestModel;
import in.manrajsingh.utilities.RequestServiceUtils;

@Service
public class RequestServiceImpl implements RequestServiceInterface {

	@Autowired
	RequestDaoInterface requestDaoImpl;

	@Autowired
	RequestServiceUtils requestUtils;

	@Override
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
	public List<RequestModel> getAllRequests() {
		List<Request> requests = requestDaoImpl.getAll();
		List<RequestModel> requestModel = requestUtils.mapRequestsToModel(requests);
		return requestModel;
	}
	
	@Override
	public RequestModel getRequestById(int id) {
		Request request = requestDaoImpl.getById(id);
		RequestModel requestModel = requestUtils.mapRequest(request);
		return requestModel;
	}
}
