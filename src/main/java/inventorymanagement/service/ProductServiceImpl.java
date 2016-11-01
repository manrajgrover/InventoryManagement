package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.constants.Constants;
import inventorymanagement.dao.ProductDaoInterface;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingProductModel;
import inventorymanagement.model.ProductModel;
import inventorymanagement.utilities.ProductServiceUtils;

@Service
public class ProductServiceImpl implements ProductServiceInterface {

  @Autowired
  ProductDaoInterface productDaoImpl;

  @Autowired
  ProductServiceUtils productServiceUtils;

  @Override
  @Transactional
  public ProductModel addProduct(IncomingProductModel productModel) throws BadRequestException {

    if (productModel.getCompany() == null || productModel.getName() == null
        || productModel.getVersion() == null || productModel.getCompany().equals("")
        || productModel.getName().equals("") || productModel.getVersion().equals("")) {
      
      throw new BadRequestException("Required parameters are either missing or invalid");
      
    }

    String name = productModel.getName();
    String company = productModel.getCompany();
    String version = productModel.getVersion();

    Product product = new Product(name, company, version);
    productDaoImpl.save(product);
    ProductModel pm = new ProductModel(product, Constants.PRODUCT_CREATED_MESSAGE);
    return pm;
  }

  @Override
  @Transactional
  public ProductModel updateProduct(int id, IncomingProductModel productModel)
      throws BadRequestException, NotFoundException {
    
    if (productModel.getCompany() == null || productModel.getName() == null
        || productModel.getVersion() == null || productModel.getCompany().equals("")
        || productModel.getName().equals("") || productModel.getVersion().equals("")) {
      
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    
    try {
      Product product = productDaoImpl.getById(id);

      productServiceUtils.mapFromUpdateProduct(productModel, product);
      productDaoImpl.update(product);

      ProductModel pm = new ProductModel(product, Constants.PRODUCT_UPDATED_MESSAGE);
      return pm;
    } catch (Exception e) {
      throw new NotFoundException("Product with given ID does not exist");
    }
  }

  @Override
  @Transactional
  public void deleteProduct(int id) throws BadRequestException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    
    Product product = new Product(id);
    productDaoImpl.delete(product);
  }

  @Override
  @Transactional
  public List<IncomingProductModel> getAllProducts() {
    
    List<Product> products = productDaoImpl.getAll();
    List<IncomingProductModel> productsModel = productServiceUtils.mapProductsToModel(products);
    
    return productsModel;
  }

  @Override
  @Transactional
  public IncomingProductModel getProductById(int id) throws BadRequestException, NotFoundException {
    if (id <=0) {
      throw new BadRequestException("Required parameters are either missing or invalid");
    }
    try {
      
      Product product = productDaoImpl.getById(id);
      IncomingProductModel productModel = productServiceUtils.mapProduct(product);
      return productModel;
      
    } catch(Exception e) {
      throw new NotFoundException("Product with given ID does not exist");
    }
  }

}
