package inventorymanagement.dao;

import java.util.List;

import inventorymanagement.entities.History;

public interface HistoryDaoInterface {

	public void save(History history);

	public void update(History history);

	public List<History> getAll();

	public History getById(int id);

}