package in.manrajsingh.dao;

import java.util.List;

import in.manrajsingh.entities.User;

public interface UserDaoInterface {
	
	public void save(User user);
	public void delete(User user);
	public void update(User user);
	
	public List<User> getAll();
	
	public User getById(int id);
	
}
