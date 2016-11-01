package inventorymanagement.service;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;

public interface HistoryServiceInterface {

  HistoryModel issueItem(IncomingHistoryModel historyModel) throws BadRequestException, NotFoundException;

  HistoryModel returnItem(int issueNumber, IncomingReturnModel historyModel)
      throws NotFoundException, BadRequestException;
}
