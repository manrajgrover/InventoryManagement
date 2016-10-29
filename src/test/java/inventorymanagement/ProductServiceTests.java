package inventorymanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.service.ProductServiceInterface;
import inventorymanagement.utilities.ProductServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class ProductServiceTests {

  @Autowired
  ProductServiceInterface productService;

  @Autowired
  ProductServiceUtils productServiceUtils;

  @Test
  public void getAllProductsTests() {
    List<IncomingProductModel> productModels = productService.getAllProducts();
    assertEquals(productModels.size(), 4);

    IncomingProductModel product = productModels.get(0);
    assertEquals(product.getId(), 1);
    assertEquals(product.getCompany(), "Apple");
    assertEquals(product.getName(), "MacBook");
    assertEquals(product.getVersion(), "2017");
  }

  @Test
  public void getProductByIdTests() {
    IncomingProductModel productModel = productService.getProductById(1);

    assertEquals(productModel.getId(), 1);
    assertEquals(productModel.getCompany(), "Apple");
    assertEquals(productModel.getName(), "MacBook");
    assertEquals(productModel.getVersion(), "2017");
  }
}
