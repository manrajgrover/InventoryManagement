package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

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
import inventorymanagement.utilities.UserServiceUtils;

@Service
public class UserServiceImpl implements UserServiceInterface {

  private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

  @Autowired
  UserDaoInterface userDaoImpl;

  @Autowired
  UserRoleDaoInterface userRoleDaoImpl;

  @Autowired
  UserServiceUtils userUtils;

  @Override
  @Transactional
  public UserModel addUser(IncomingUserModel userModel) throws BadRequestException {
    if (userModel.getName() == null || userModel.getEmail() == null) {
      throw new BadRequestException("Required parameters are invalid");
    }
    LOG.debug("Parameters are valid");
    String name = userModel.getName();
    String email = userModel.getEmail();
    String contact = userModel.getContact();
    Role role = new Role(2);
    User user = new User(name, email, contact);
    UserRole userRole = new UserRole(role, user);
    LOG.debug("Saving user");
    userDaoImpl.save(user);
    userRoleDaoImpl.save(userRole);
    LOG.debug("Saved user and its role");
    UserModel addUser = new UserModel(user, Constants.USER_CREATED_MESSAGE);
    return addUser;
  }

  @Override
  @Transactional
  public UserModel addUserIfNotExist(IncomingUserModel userModel) throws BadRequestException {
    User user = userDaoImpl.getUserByEmail(userModel.getEmail());
    if (user == null) {
      return addUser(userModel);
    } else {
      UserModel um = new UserModel(user);
      return um;
    }
  }

  @Override
  @Transactional
  public UserModel updateUser(int id, IncomingUserModel userModel) {
    User user = userDaoImpl.getById(id);

    userUtils.mapFromUpdateUser(userModel, user);
    userDaoImpl.update(user);

    UserModel um = new UserModel(user, Constants.USER_UPDATED_MESSAGE);
    return um;
  }

  @Override
  @Transactional
  public List<UserModel> getAllUsers() {
    List<User> users = userDaoImpl.getAll();
    List<UserModel> usersModel = userUtils.mapUsersToModels(users);
    return usersModel;
  }

  @Override
  @Transactional
  public void deleteUser(int id) {
    User user = new User(id);
    userDaoImpl.delete(user);
  }

  @Override
  @Transactional
  public UserModel getUserById(int id) {
    User user = userDaoImpl.getById(id);
    UserModel userModel = userUtils.mapUser(user);
    return userModel;
  }

}
