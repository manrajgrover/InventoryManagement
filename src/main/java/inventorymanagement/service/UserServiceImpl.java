package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventorymanagement.dao.UserDaoInterface;
import inventorymanagement.entities.User;
import inventorymanagement.model.UserModel;
import inventorymanagement.utilities.UserServiceUtils;

/**
 * @see UserServiceInterface
 * @author manrajsingh
 *
 */
@Service
public class UserServiceImpl implements UserServiceInterface {

  /**
   * {@link UserDaoInterface}
   */
  @Autowired
  UserDaoInterface userDaoImpl;

  /**
   * {@link UserServiceUtils}
   */
  @Autowired
  UserServiceUtils userUtils;

  /*
   * (non-Javadoc)
   * 
   * @see inventorymanagement.service.UserServiceInterface#getAllUsers()
   */
  @Override
  @Transactional
  public List<UserModel> getAllUsers() {
    List<User> users = userDaoImpl.getAll();
    List<UserModel> usersModel = userUtils.mapUsersToModels(users);
    return usersModel;
  }

}
