package inventorymanagement.dao;

import java.util.List;

import inventorymanagement.entities.User;

public interface UserDaoInterface {

	public void save(User user);

	public void delete(User user);

	public void update(User user);

	public List<User> getAll();

	public User getById(int id);

	public User getUserByEmail(String email);

}
