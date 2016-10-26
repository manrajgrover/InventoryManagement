package inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public ProductModel addProduct(IncomingProductModel productModel) {
		String name = productModel.getName();
		String company = productModel.getCompany();
		String version = productModel.getVersion();

		Product product = new Product(name, company, version);
		productDaoImpl.save(product);
		ProductModel pm = new ProductModel(product, Constants.PRODUCT_CREATED_MESSAGE);
		return pm;
	}

	@Override
	public ProductModel updateProduct(int id, IncomingProductModel productModel) {
		Product product = productDaoImpl.getById(id);

		productServiceUtils.mapFromUpdateProduct(productModel, product);
		productDaoImpl.update(product);

		ProductModel pm = new ProductModel(product, Constants.PRODUCT_UPDATED_MESSAGE);
		return pm;
	}

	@Override
	public void deleteProduct(int id) {
		Product product = new Product(id);
		productDaoImpl.delete(product);
	}

	@Override
	public List<IncomingProductModel> getAllProducts() {
		List<Product> products = productDaoImpl.getAll();
		List<IncomingProductModel> productsModel = productServiceUtils.mapProductsToModel(products);
		return productsModel;
	}

	@Override
	public IncomingProductModel getProductById(int id) {
		Product product = productDaoImpl.getById(id);
		IncomingProductModel productModel = productServiceUtils.mapProduct(product);
		return productModel;
	}

}
