package inventorymanagement.service;

import java.util.List;

import inventorymanagement.model.UserModel;

/**
 * Service to get all users from the database
 * 
 * @author manrajsingh
 *
 */
public interface UserServiceInterface {
  /**
   * Method to get all users from the database
   * 
   * @return {@link UserModel} List of Users mapped to UserModel
   */
  public List<UserModel> getAllUsers();

}
