package in.manrajsingh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.manrajsingh.model.IncomingRequestModel;
import in.manrajsingh.model.RequestModel;
import in.manrajsingh.service.RequestServiceInterface;
import inti.ws.spring.exception.client.BadRequestException;

@Controller
public class RequestController {

	@Autowired
	RequestServiceInterface requestService;

	@RequestMapping(value = "/requests", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public RequestModel create(@RequestBody IncomingRequestModel request) throws BadRequestException {
		return requestService.addRequest(request);
	}

	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<RequestModel> getAll() {
		return requestService.getAllRequests();
	}

	@RequestMapping(value = "/requests/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RequestModel getById(@PathVariable int id) {
		return requestService.getRequestById(id);
	}
}
