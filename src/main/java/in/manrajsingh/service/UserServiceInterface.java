package in.manrajsingh.service;

import java.util.List;

import in.manrajsingh.model.IncomingUserModel;
import in.manrajsingh.model.UserModel;

public interface UserServiceInterface {
	
	public UserModel addUser(String name, String email, String contact);
	public List<UserModel> getAllUsers();
	public UserModel getUserById(int id);
	public void deleteUser(int id);
	public UserModel updateUser(int id, IncomingUserModel userModel);
	
}
