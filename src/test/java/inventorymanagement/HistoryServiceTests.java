package inventorymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.constants.Constants;
import inventorymanagement.model.HistoryModel;
import inventorymanagement.model.IncomingHistoryModel;
import inventorymanagement.model.IncomingReturnModel;
import inventorymanagement.service.HistoryServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class HistoryServiceTests {

  @Autowired
  HistoryServiceInterface historyService;

  @Test
  public void issueItemTests() throws BadRequestException, NotFoundException {
    IncomingHistoryModel historyModel = new IncomingHistoryModel();
    historyModel.setProductId(3);
    historyModel.setUserId(1);
    historyModel.setProductTag("XYZ");
    
    HistoryModel hm = historyService.issueItem(historyModel);
    
    assertTrue(hm.getId() > 0);
    assertEquals(hm.getAvailability(), true);
    assertEquals(hm.getMessage(), Constants.ITEM_AVAILABLE_ISSUED);
  }
  
  @Test
  public void returnItemTests() throws NotFoundException, BadRequestException {
    IncomingReturnModel returnModel = new IncomingReturnModel();
    int issueId = 1;
    returnModel.setProductTag("PQR");

    HistoryModel hm = historyService.returnItem(issueId, returnModel);

    assertEquals(hm.getAvailability(), true);
    assertEquals(hm.getMessage(), Constants.ITEM_RETURNED);
  }
}
