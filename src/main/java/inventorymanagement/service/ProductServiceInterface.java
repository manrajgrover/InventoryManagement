package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;

public interface ProductServiceInterface {

  public ProductModel addProduct(IncomingProductModel productModel) throws BadRequestException;

  public ProductModel updateProduct(int id, IncomingProductModel productModel) throws BadRequestException, NotFoundException;

  public void deleteProduct(int id) throws BadRequestException;

  public List<IncomingProductModel> getAllProducts();

  public IncomingProductModel getProductById(int id) throws BadRequestException, NotFoundException;
}
