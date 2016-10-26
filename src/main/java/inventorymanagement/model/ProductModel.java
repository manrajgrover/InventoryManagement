
package inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import inventorymanagement.entities.Product;

public class ProductModel {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private int id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String company;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String version;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	public ProductModel() {
	}

	public ProductModel(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public ProductModel(Product product, String message) {
		this.id = product.getId();
		this.name = product.getName();
		this.company = product.getCompany();
		this.version = product.getVersion();
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}