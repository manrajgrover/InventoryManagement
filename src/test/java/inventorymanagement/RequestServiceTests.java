package inventorymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
import inventorymanagement.model.IncomingRequestModel;
import inventorymanagement.model.IncomingUpdateRequest;
import inventorymanagement.model.RequestModel;
import inventorymanagement.service.RequestServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class RequestServiceTests {

  @Autowired
  private RequestServiceInterface requestService;
  
  @Test
  public void addRequestTests() throws BadRequestException {
    IncomingRequestModel requestModel = new IncomingRequestModel();
    requestModel.setProductId(3);
    requestModel.setUserId(1);
    RequestModel rm = requestService.addRequest(requestModel);
    
    assertTrue(rm.getId() > 0);
    assertEquals(rm.getMessage(), Constants.REQUEST_CREATED_MESSAGE);
    assertEquals(rm.getProductCompany(), "Apple");
    assertEquals(rm.getProductName(), "iPhone");
    assertEquals(rm.getVersion(), "7");
    assertEquals(rm.getReply(), "");
  }
  
  @Test
  public void updateRequestTests() throws BadRequestException, NotFoundException {
    IncomingUpdateRequest requestModel = new IncomingUpdateRequest();
    requestModel.setReply("Approved");
    
    RequestModel rm = requestService.updateRequest(2, requestModel);
    
    assertTrue(rm.getId() > 0);
    assertEquals(rm.getMessage(), Constants.REQUEST_UPDATED_MESSAGE);
    assertEquals(rm.getProductCompany(), "Apple");
    assertEquals(rm.getProductName(), "iPhone");
    assertEquals(rm.getVersion(), "7");
    assertEquals(rm.getReply(), "Approved");
  }

  @Test
  public void getAllRequestsTests() {
    List<RequestModel> requests = requestService.getAllRequests();
    assertEquals(requests.size(), 2);

    RequestModel request = requests.get(0);
    assertEquals(request.getUserName(), "Manraj Singh Grover");
    assertEquals(request.getId(), 1);
    assertEquals(request.getProductCompany(), "OnePlus");
    assertEquals(request.getProductName(), "Three");
    assertEquals(request.getReply(), "YES");
  }

  @Test
  public void getRequestsByIdTests() throws BadRequestException, NotFoundException {
    RequestModel request = requestService.getRequestById(1);

    assertEquals(request.getUserName(), "Manraj Singh Grover");
    assertEquals(request.getId(), 1);
    assertEquals(request.getProductCompany(), "OnePlus");
    assertEquals(request.getProductName(), "Three");
    assertEquals(request.getReply(), "YES");
  }
}
