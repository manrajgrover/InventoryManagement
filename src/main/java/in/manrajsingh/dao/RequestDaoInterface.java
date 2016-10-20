package in.manrajsingh.dao;

import java.util.List;

import in.manrajsingh.entities.Request;

public interface RequestDaoInterface {
	
	public void save(Request request);
	public void update(Request request);
	
	public List<Request> getAll();
	
	public Request getById(int id);
	
}
