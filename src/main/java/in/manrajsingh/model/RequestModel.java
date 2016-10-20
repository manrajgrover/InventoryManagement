package in.manrajsingh.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.manrajsingh.entities.Request;

public class RequestModel {
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private int id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String userName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String productName;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String productCompany;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String version;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private boolean status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String reply;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String message;
	
	public RequestModel() {}
	
	public RequestModel(Request request, String message) {
		System.out.println(message);
		this.id = request.getId();
		this.reply = request.getReply();
		this.status = request.isStatus();
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCompany() {
		return productCompany;
	}

	public void setProductCompany(String productCompany) {
		this.productCompany = productCompany;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
