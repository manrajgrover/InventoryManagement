package inventorymanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.model.RequestModel;
import inventorymanagement.service.RequestServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class RequestServiceTests {
  
  @Autowired
  private RequestServiceInterface requestService;
  
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
  public void getRequestsByIdTests() {
    RequestModel request = requestService.getRequestById(1);
    
    assertEquals(request.getUserName(), "Manraj Singh Grover");
    assertEquals(request.getId(), 1);
    assertEquals(request.getProductCompany(), "OnePlus");
    assertEquals(request.getProductName(), "Three");
    assertEquals(request.getReply(), "YES");
  }
}
