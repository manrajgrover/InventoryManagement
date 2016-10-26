package inventorymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.service.HistoryServiceInterface;

@RestController
public class HistoryController {

	@Autowired
	HistoryServiceInterface historyService;

	@RequestMapping(value = "/history", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public HistoryModel create(@RequestBody IncomingHistoryModel historyModel) {
		return historyService.issueItem(historyModel);
	}

	@RequestMapping(value = "/history/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public HistoryModel update(@PathVariable int id, @RequestBody IncomingReturnModel historyModel)
			throws NotFoundException {
		return historyService.returnItem(id, historyModel);
	}
}