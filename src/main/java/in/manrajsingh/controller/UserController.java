package in.manrajsingh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.manrajsingh.model.IncomingUserModel;
import in.manrajsingh.model.UserModel;
import in.manrajsingh.service.UserServiceInterface;

@Controller
public class UserController {
	
	@Autowired
	UserServiceInterface userService;
	
	@RequestMapping(value="/users", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel create(String name, String email, String contact) {
		return userService.addUser(name, email, contact);
	}
	
	@RequestMapping(value="/users", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<UserModel> getAll() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UserModel getById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UserModel updateUser(@PathVariable int id, @RequestBody IncomingUserModel userModel) {
		return userService.updateUser(id, userModel);
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}
}
