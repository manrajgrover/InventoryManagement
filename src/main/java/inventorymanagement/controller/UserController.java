package inventorymanagement.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;

@RestController
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	UserServiceInterface userService;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel create(@RequestBody IncomingUserModel userModel) throws BadRequestException {
		LOG.info("Request received to add a user");
		UserModel user = userService.addUser(userModel);
		LOG.info("Request to add a user successful");
		return user;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<UserModel> getAll() {
		LOG.info("Request received to get all user");
		List<UserModel> users = userService.getAllUsers();
		LOG.info("Request to get all successful");
		return users;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UserModel getById(@PathVariable int id) {
		LOG.info("Request received to get information of particular user");
		UserModel user = userService.getUserById(id);
		LOG.info("Request to get information of particular user successful");
		return user;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UserModel updateUser(@PathVariable int id, @RequestBody IncomingUserModel userModel) {
		LOG.info("Request received to get a user's information");
		UserModel user = userService.updateUser(id, userModel);
		LOG.info("Request to get a user's information successful");
		return user;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable int id) {
		LOG.info("Request received to delete a user");
		userService.deleteUser(id);
		LOG.info("Request to delete a user successful");
	}
}
