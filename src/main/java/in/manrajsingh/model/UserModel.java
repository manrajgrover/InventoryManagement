package in.manrajsingh.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.manrajsingh.entities.User;

public class UserModel {

	private int id;	
	private String name;
	private String email;
	private String contact;
	
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private String message;
	
	public UserModel() { }
	
	public UserModel(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.contact = user.getContact();
	}
	
	public UserModel(User user, String message) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.contact = user.getContact();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
