package in.manrajsingh.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.manrajsingh.entities.User;
import in.manrajsingh.model.UserModel;


@Service
public class UserServiceUtils {
	
	public List<UserModel> mapUsersToModels(List<User> users) {
		List<UserModel> userModels = new ArrayList<>();
		
		for(User user : users) {
			userModels.add(mapUser(user));
		}
		return userModels;
	}
	
	private UserModel mapUser(User user) {
		UserModel userModel = new UserModel();
		userModel.setId(user.getId());
		userModel.setName(user.getName());
		userModel.setEmail(user.getEmail());
		userModel.setContact(user.getContact());
		return userModel;
	}
	
}
