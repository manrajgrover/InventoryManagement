package in.manrajsingh.service;

import java.util.List;

import in.manrajsingh.model.UserModel;

public interface UserServiceInterface {
	
	public UserModel addUser(String name, String email, String contact);
	public List<UserModel> getAllUsers();
	// public UpdateUserModel updateJob(int userId, User user);
	
}
