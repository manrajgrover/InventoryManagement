package in.manrajsingh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manrajsingh.constants.Constants;
import in.manrajsingh.dao.UserDaoInterface;
import in.manrajsingh.entities.User;
import in.manrajsingh.model.IncomingUserModel;
import in.manrajsingh.model.UserModel;
import in.manrajsingh.utilities.UserServiceUtils;

@Service
public class UserServiceImpl implements UserServiceInterface {
	
	@Autowired
	UserDaoInterface userDaoImpl;
	
	@Autowired
	UserServiceUtils userUtils;
	
	@Override
	public UserModel addUser(String name, String email, String contact) {
		User user = new User(name, email, contact);
		userDaoImpl.save(user);
		UserModel addUser = new UserModel(user, Constants.USER_CREATED_MESSAGE);
		return addUser;
	}
	
	@Override
	public UserModel updateUser(int id, IncomingUserModel userModel) {
		User user = userDaoImpl.getById(id);
		
		userUtils.mapFromUpdateUser(userModel, user);
		userDaoImpl.update(user);
		
		UserModel um = new UserModel(user, Constants.USER_UPDATED_MESSAGE);
		return um;
	}
	
	@Override
	public List<UserModel> getAllUsers() {
		List<User> users = userDaoImpl.getAll();
		List<UserModel> usersModel = userUtils.mapUsersToModels(users);
		return usersModel;
	}

	@Override
	public void deleteUser(int id) {
		User user = new User(id);
		userDaoImpl.delete(user);
	}

	@Override
	public UserModel getUserById(int id) {
		User user = userDaoImpl.getById(id);
		UserModel userModel = userUtils.mapUser(user);
		return userModel;
	}
	
}
