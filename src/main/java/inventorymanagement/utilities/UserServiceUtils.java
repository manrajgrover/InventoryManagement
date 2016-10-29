package inventorymanagement.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import inventorymanagement.entities.Role;
import inventorymanagement.entities.User;
import inventorymanagement.entities.UserRole;
import inventorymanagement.model.UserModel;

@Service
public class UserServiceUtils {

  public List<UserModel> mapUsersToModels(List<User> users) {
    List<UserModel> userModels = new ArrayList<>();

    for (User user : users) {
      userModels.add(mapUser(user));
    }
    return userModels;
  }

  public UserModel mapUser(User user) {
    UserModel userModel = new UserModel();
    userModel.setId(user.getId());
    userModel.setName(user.getName());
    userModel.setEmail(user.getEmail());
    return userModel;
  }

  public Boolean checkIfAdmin(UserModel userModel) {

    Set<UserRole> roles = userModel.getRole();
    Boolean admin = false;

    for (UserRole userRole : roles) {
      Role role = userRole.getRole();
      if (role.getId() == 1) {
        admin = true;
      }
    }

    return admin;
  }

}
