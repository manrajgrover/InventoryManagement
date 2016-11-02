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
import inventorymanagement.dao.ProductDaoInterface;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;
import inventorymanagement.service.ProductServiceInterface;
import inventorymanagement.utilities.ProductServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
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
  public void addProductTests() throws BadRequestException {
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
  
  @Test(expected = BadRequestException.class)
  public void addProductNoCompanyTests() throws BadRequestException {
    IncomingProductModel productModel = new IncomingProductModel();
    productModel.setName("One");
    productModel.setVersion("2013");
    
    ProductModel pm = productService.addProduct(productModel);
  }
  
  @Test
  public void updateProductTests() throws BadRequestException, NotFoundException {
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
  
  @Test(expected = BadRequestException.class)
  public void updateProductNoCompanyTests() throws BadRequestException, NotFoundException {
    IncomingProductModel productModel = new IncomingProductModel();
    productModel.setName("One");
    productModel.setVersion("2014");
    
    ProductModel pm = productService.updateProduct(2, productModel);
  }
  
  @Test(expected = NotFoundException.class)
  public void updateProductNotFoundTests() throws BadRequestException, NotFoundException {
    IncomingProductModel productModel = new IncomingProductModel();
    productModel.setCompany("OnePlus");
    productModel.setName("One");
    productModel.setVersion("2014");
    
    ProductModel pm = productService.updateProduct(200, productModel);
  }
  
  @Test
  public void deleteProductTests() throws BadRequestException {
    productService.deleteProduct(1);
    Product product = productDao.getById(1);
    assertEquals(product, null);
  }
  
  @Test(expected = BadRequestException.class)
  public void deleteProductNoIdTests() throws BadRequestException {
    productService.deleteProduct(-1);
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
  public void getProductByIdTests() throws BadRequestException, NotFoundException {
    IncomingProductModel productModel = productService.getProductById(1);

    assertEquals(productModel.getId(), 1);
    assertEquals(productModel.getCompany(), "Apple");
    assertEquals(productModel.getName(), "MacBook");
    assertEquals(productModel.getVersion(), "2017");
  }
  
  @Test(expected = BadRequestException.class)
  public void getProductByIdNoIdTests() throws BadRequestException, NotFoundException {
    IncomingProductModel productModel = productService.getProductById(-1);
  }
  
  @Test(expected = NotFoundException.class)
  public void getProductByIdNoExistingIdTests() throws BadRequestException, NotFoundException {
    IncomingProductModel productModel = productService.getProductById(200);
  }
}
