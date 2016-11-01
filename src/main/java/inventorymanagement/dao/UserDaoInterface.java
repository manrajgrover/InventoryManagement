package inventorymanagement.dao;

import java.util.List;

import inventorymanagement.entities.User;

public interface UserDaoInterface {

  public void save(User user);

  public List<User> getAll();

  public User getUserByEmail(String email);

  User getById(int id);

  public void refresh(User user);
}
