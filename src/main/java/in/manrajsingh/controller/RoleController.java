package in.manrajsingh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.manrajsingh.entities.Role;
import in.manrajsingh.service.RoleServiceInterface;

@Controller
public class RoleController {
	
	@Autowired
	RoleServiceInterface roleService;
	
	@RequestMapping(value="/roles", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Role> getAll() {
		return roleService.getAllRoles();
	}
	
}
