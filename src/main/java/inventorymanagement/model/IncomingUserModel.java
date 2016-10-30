package inventorymanagement.model;

import java.util.HashSet;
import java.util.Set;

import inventorymanagement.entities.UserRole;

public class IncomingUserModel {
  private String name;
  private String email;
  private Set<UserRole> userRoles = new HashSet<UserRole>(0);

  public IncomingUserModel() {};

  public IncomingUserModel(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }
}
