package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
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
  UserServiceUtils userUtils;

  @Override
  @Transactional
  public List<UserModel> getAllUsers() {
    List<User> users = userDaoImpl.getAll();
    List<UserModel> usersModel = userUtils.mapUsersToModels(users);
    return usersModel;
  }

}
