package in.manrajsingh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manrajsingh.constants.Constants;
import in.manrajsingh.dao.UserDaoInterface;
import in.manrajsingh.entities.User;
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
	public List<UserModel> getAllUsers() {
		List<User> users = userDaoImpl.getAll();
		List<UserModel> usersModel = userUtils.mapUsersToModels(users);
		return usersModel;
	}
	
	/*@Override
	public UserModel updateUser(int userId, User user) {
		UpdateUserModel user = userUtils.mapUpdateUser(userId, user);
		return user;
	}*/
	
}
