package inventorymanagement.service;

import java.util.List;

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;

public interface UserServiceInterface {
  public List<UserModel> getAllUsers();

}
