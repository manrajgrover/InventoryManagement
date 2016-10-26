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

import inventorymanagement.entities.Role;
import inventorymanagement.service.RoleServiceInterface;

@RestController
public class RoleController {

  private static final Logger LOG = Logger.getLogger(RoleController.class);

  @Autowired
  RoleServiceInterface roleService;

  @RequestMapping(value = "/roles", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Role> getAll() {
    LOG.info("Request received for getting roles");
    List<Role> roles = roleService.getAllRoles();
    LOG.info("Request for getting roles successful");
    return roles;
  }

}
