package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;

/**
 * Service that allows admin to read, add, update and delete items
 * @author manrajsingh
 *
 */
public interface ProductServiceInterface {

  /**
   * Method to add a product to database
   * 
   * @param productModel {@link IncomingProductModel} Model containing data to add a new product to database
   * @return {@link ProductModel}
   * @throws BadRequestException Thrown when any of the field is empty
   */
  public ProductModel addProduct(IncomingProductModel productModel) throws BadRequestException;
  
  /**
   * Method to update a product in database
   * 
   * @param id {@link Integer} Id of Product
   * @param productModel {@link IncomingProductModel} Model containing data to update a product in database
   * @return {@link ProductModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when product with given id does not exist in database
   */
  public ProductModel updateProduct(int id, IncomingProductModel productModel) throws BadRequestException, NotFoundException;

  /**
   * Method to delete a product and its children items in database
   * 
   * @param id {@link Integer} Id of product
   * @throws BadRequestException Thrown when any of the field is empty
   */
  public void deleteProduct(int id) throws BadRequestException;

  /**
   * Method to get all products from the database
   * @return {@link IncomingProductModel} List of Products mapped to IncomingProductModel
   */
  public List<IncomingProductModel> getAllProducts();

  /**
   * Method to get products with given id
   * @param id {@link Integer}
   * @return {@link IncomingProductModel}
   * @throws BadRequestException Thrown when any of the field is empty
   * @throws NotFoundException Thrown when product with given id does not exist in database
   */
  public IncomingProductModel getProductById(int id) throws BadRequestException, NotFoundException;
}
