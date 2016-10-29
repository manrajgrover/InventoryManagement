package inventorymanagement.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;

@RestController
public class UserController {

  private static final Logger LOG = Logger.getLogger(UserController.class);

  @Autowired
  UserServiceInterface userService;

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<UserModel> getAll() {
    LOG.info("Request received to get all user");
    List<UserModel> users = userService.getAllUsers();
    LOG.info("Request to get all successful");
    return users;
  }

}
