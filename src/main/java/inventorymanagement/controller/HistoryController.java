package inventorymanagement.controller;

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
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.service.HistoryServiceInterface;

@RestController
public class HistoryController {
  
  private static final Logger LOG = Logger.getLogger(RequestController.class);

  @Autowired
  HistoryServiceInterface historyService;

  @RequestMapping(value = "/history", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public HistoryModel create(@RequestBody IncomingHistoryModel historyModel, HttpSession session)
      throws UnauthorizedException, BadRequestException, NotFoundException {
    LOG.info("Request received to issue an item");
    
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    
    return historyService.issueItem(historyModel);
  }

  @RequestMapping(value = "/history/{id}", method = RequestMethod.PATCH)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public HistoryModel update(@PathVariable int id, @RequestBody IncomingReturnModel historyModel,
      HttpSession session) throws NotFoundException, UnauthorizedException, BadRequestException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    
    return historyService.returnItem(id, historyModel);
  }
}
