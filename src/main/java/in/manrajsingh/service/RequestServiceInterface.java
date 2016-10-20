package in.manrajsingh.service;

import java.util.List;

import in.manrajsingh.model.IncomingRequestModel;
import in.manrajsingh.model.RequestModel;

public interface RequestServiceInterface {
	public RequestModel addRequest(IncomingRequestModel requestModel);

	public List<RequestModel> getAllRequests();

	RequestModel getRequestById(int id);
}
