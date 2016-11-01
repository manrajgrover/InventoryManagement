package inventorymanagement.utilities;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.constants.Constants;
import inventorymanagement.dao.UserDaoInterface;
import inventorymanagement.dao.UserRoleDaoInterface;
import inventorymanagement.entities.Role;
import inventorymanagement.entities.User;
import inventorymanagement.entities.UserRole;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceImpl;

@Service
public class OAuthServiceUtils {

  private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

  @Autowired
  UserDaoInterface userDaoImpl;

  @Autowired
  UserRoleDaoInterface userRoleDaoImpl;

  public UserModel addUser(IncomingUserModel userModel) throws BadRequestException {
    if (userModel.getName() == null || userModel.getEmail() == null
        || userModel.getName().equals("") || userModel.getEmail().equals("")) {
      throw new BadRequestException("Required parameters are invalid");
    }
    LOG.debug("Parameters are valid");
    String name = userModel.getName();
    String email = userModel.getEmail();
    Role role = new Role(2, "user");
    User user = new User(name, email);
    UserRole userRole = new UserRole(role, user);
    LOG.debug("Saving user");
    userDaoImpl.save(user);
    userRoleDaoImpl.save(userRole);
    userRoleDaoImpl.refresh(userRole);
    userDaoImpl.refresh(user);
    LOG.debug("Saved user and its role");
    UserModel addUser = new UserModel(user, Constants.USER_CREATED_MESSAGE);
    return addUser;
  }

  public UserModel addUserIfNotExist(IncomingUserModel userModel) throws BadRequestException {
    if (userModel.getEmail() == null || userModel.getEmail().equals("")) {
      throw new BadRequestException("Email provided is invalid");
    }

    User user = userDaoImpl.getUserByEmail(userModel.getEmail());
    if (user == null) {
      return addUser(userModel);
    } else {
      UserModel um = new UserModel(user);
      return um;
    }
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
