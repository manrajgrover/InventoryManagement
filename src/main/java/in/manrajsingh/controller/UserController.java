package in.manrajsingh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.manrajsingh.model.UserModel;
import in.manrajsingh.service.UserServiceInterface;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	UserServiceInterface userService;
	
	@RequestMapping(value="/new", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel create(String name, String email, String contact) {
		return userService.addUser(name, email, contact);
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<UserModel> getAll() {
		return userService.getAllUsers();
	}
	
}
