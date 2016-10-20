package in.manrajsingh.service;

import java.util.List;

import in.manrajsingh.model.IncomingProductModel;
import in.manrajsingh.model.ProductModel;

public interface ProductServiceInterface {

	public ProductModel addProduct(IncomingProductModel productModel);

	public ProductModel updateProduct(int id, IncomingProductModel productModel);
	
	public void deleteProduct(int id);
	
	public List<IncomingProductModel> getAllProducts();
	public IncomingProductModel getProductById(int id);
}
