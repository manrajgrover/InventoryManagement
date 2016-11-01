package inventorymanagement.dao;

import inventorymanagement.entities.UserRole;

public interface UserRoleDaoInterface {
  public void save(UserRole user);

  public void refresh(UserRole userRole);
}
