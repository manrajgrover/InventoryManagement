package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;

public interface UserServiceInterface {
	public List<UserModel> getAllUsers();

	public UserModel getUserById(int id);

	public void deleteUser(int id);

	public UserModel updateUser(int id, IncomingUserModel userModel);

	public UserModel addUserIfNotExist(IncomingUserModel userModel) throws BadRequestException;

	public UserModel addUser(IncomingUserModel userModel) throws BadRequestException;

}
