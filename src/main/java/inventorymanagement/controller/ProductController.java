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

  /**
   * {@link Logger}
   */
  private static final Logger LOG = Logger.getLogger(ProductController.class);

  /**
   * {@link ProductServiceInterface}
   */
  @Autowired
  ProductServiceInterface productService;

  /**
   * Controller method to add new products to the database
   * 
   * @param productModel {@link IncomingProductModel} contains Product Company, Product Name and its
   *        version
   * @param session {@link HttpSession}
   * @return {@link ProductModel}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   */
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

  /**
   * Controller method to update details of a product
   * 
   * @param id {@link Integer} Id of product
   * @param productModel {@link IncomingProductModel} contains Product Company, Product Name and its
   *        version
   * @param session {@link HttpSession}
   * @return {@link ProductModel}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no product with given id is found
   */
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

  /**
   * Controller method to delete a product from the database
   * 
   * @param id {@link Integer} Id of product
   * @param session {@link HttpSession}
   * @throws UnauthorizedException Thrown when user is not logged in or not an admin
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable int id, HttpSession session)
      throws UnauthorizedException, BadRequestException {

    Boolean admin = (Boolean) session.getAttribute(Constants.SESSION_ADMIN);

    if (admin == false) {
      throw new UnauthorizedException("Unauthorized access");
    }

    LOG.info("Request received for deleting a product");

    productService.deleteProduct(id);

    LOG.info("Request for deleting a product successful");
  }

  /**
   * Controller method to get all products from the database
   * 
   * @return {@link IncomingProductModel} List of all Products mapped to IncomingProductModel
   */
  @RequestMapping(value = "/products", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<IncomingProductModel> getAll() {

    LOG.info("Request received for getting all products");

    List<IncomingProductModel> products = productService.getAllProducts();

    LOG.info("Request for getting all products successful");

    return products;
  }

  /**
   * Controller method to get details of particular product
   * 
   * @param id {@link Integer} Id of Product
   * @return {@link IncomingProductModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when no product with given id is found
   */
  @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public IncomingProductModel getById(@PathVariable int id)
      throws BadRequestException, NotFoundException {

    LOG.info("Request received for getting product by id");

    IncomingProductModel product = productService.getProductById(id);

    LOG.info("Request for getting product by id successful");

    return product;
  }
}
