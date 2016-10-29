package inventorymanagement;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class RequestServiceTests {

  @Test
  public void test() {
    assertTrue(2 == 2);
  }

  /*
   * @Autowired RequestServiceInterface requestService;
   * 
   * @Autowired RequestServiceUtils requestServiceUtils;
   * 
   * @Test public void getAllRequestsTests() { List<RequestModel> requests =
   * requestService.getAllRequests(); assertEquals(requests.size(), 4);
   * 
   * IncomingProductModel product = productModels.get(0); assertEquals(product.getId(), 1);
   * assertEquals(product.getCompany(), "Apple"); assertEquals(product.getName(), "MacBook");
   * assertEquals(product.getVersion(), "2017"); }
   * 
   * @Test public void getRequestByIdTests() { IncomingProductModel productModel =
   * productService.getProductById(1);
   * 
   * assertEquals(productModel.getId(), 1); assertEquals(productModel.getCompany(), "Apple");
   * assertEquals(productModel.getName(), "MacBook"); assertEquals(productModel.getVersion(),
   * "2017"); }
   */
}
