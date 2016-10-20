package in.manrajsingh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.manrajsingh.model.IncomingProductModel;
import in.manrajsingh.model.ProductModel;
import in.manrajsingh.service.ProductServiceInterface;


@Controller
public class ProductController {
	
	@Autowired
	ProductServiceInterface productService;
	
	@RequestMapping(value="/products", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public ProductModel create(@RequestBody IncomingProductModel productModel) {
		return productService.addProduct(productModel);
	}
	
	@RequestMapping(value="/products/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ProductModel update(@PathVariable int id, @RequestBody IncomingProductModel productModel) {
		System.out.println(productModel.getCompany());
		return productService.updateProduct(id, productModel);
	}
	
	@RequestMapping(value="/products/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		productService.deleteProduct(id);
	}
	
	@RequestMapping(value="/products", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<IncomingProductModel> getAll() {
		return productService.getAllProducts();
	}
	
	@RequestMapping(value="/products/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public IncomingProductModel getById(@PathVariable int id) {
		return productService.getProductById(id);
	}
}
