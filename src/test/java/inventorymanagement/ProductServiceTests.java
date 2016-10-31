package inventorymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.constants.Constants;
import inventorymanagement.dao.ProductDaoInterface;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;
import inventorymanagement.service.ProductServiceInterface;
import inventorymanagement.utilities.ProductServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestComponent
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class ProductServiceTests {

  @Autowired
  ProductServiceInterface productService;
  
  @Autowired
  ProductDaoInterface productDao;

  @Autowired
  ProductServiceUtils productServiceUtils;
  
  @Test
  @Rollback(true)
  public void addProductTests() {
    IncomingProductModel productModel = new IncomingProductModel();
    productModel.setCompany("OnePlus");
    productModel.setName("One");
    productModel.setVersion("2013");
    
    ProductModel pm = productService.addProduct(productModel);
    assertTrue(pm.getId() > 0);
    assertEquals(pm.getCompany(), "OnePlus");
    assertEquals(pm.getName(), "One");
    assertEquals(pm.getVersion(), "2013");
    assertEquals(pm.getMessage(), Constants.PRODUCT_CREATED_MESSAGE);
  }
  
  @Test
  @Rollback(true)
  public void updateProductTests() {
    IncomingProductModel productModel = new IncomingProductModel();
    productModel.setCompany("OnePlus");
    productModel.setName("One");
    productModel.setVersion("2014");
    
    ProductModel pm = productService.updateProduct(2, productModel);
    Product product = productDao.getById(2);
    
    assertEquals(product.getCompany(), "OnePlus");
    assertEquals(product.getName(), "One");
    assertEquals(product.getVersion(), "2014");
    assertEquals(pm.getMessage(), Constants.PRODUCT_UPDATED_MESSAGE);
  }
  
  @Test
  @Rollback(true)
  public void deleteProductTests() {
    productService.deleteProduct(2);
    Product product = productDao.getById(2);
    assertEquals(product, null);
  }

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
