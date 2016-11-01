package inventorymanagement.controller;

import java.util.List;

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
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;
import inventorymanagement.service.ProductServiceInterface;

@RestController
public class ProductController {

  private static final Logger LOG = Logger.getLogger(ProductController.class);

  @Autowired
  ProductServiceInterface productService;

  @RequestMapping(value = "/products", method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public ProductModel create(@RequestBody IncomingProductModel productModel, HttpSession session)
      throws UnauthorizedException, BadRequestException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received for adding a product");
    ProductModel product = productService.addProduct(productModel);
    LOG.info("Request for adding a product successful");
    return product;
  }

  @RequestMapping(value = "/products/{id}", method = RequestMethod.PATCH)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ProductModel update(@PathVariable int id, @RequestBody IncomingProductModel productModel,
      HttpSession session) throws UnauthorizedException, BadRequestException, NotFoundException {
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    LOG.info("Request received for updating a product");
    ProductModel product = productService.updateProduct(id, productModel);
    LOG.info("Request for updating a product successful");
    return product;
  }

  @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable int id, HttpSession session) throws UnauthorizedException, BadRequestException {
    
    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);
    
    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }
    
    LOG.info("Request received for deleting a product");
    
    productService.deleteProduct(id);
    
    LOG.info("Request for deleting a product successful");
  }

  @RequestMapping(value = "/products", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<IncomingProductModel> getAll() {
    
    LOG.info("Request received for getting all products");
    
    List<IncomingProductModel> products = productService.getAllProducts();
    
    LOG.info("Request for getting all products successful");
    
    return products;
  }

  @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public IncomingProductModel getById(@PathVariable int id) throws BadRequestException, NotFoundException {
    
    LOG.info("Request received for getting product by id");
    
    IncomingProductModel product = productService.getProductById(id);
    
    LOG.info("Request for getting product by id successful");
    
    return product;
  }
}
