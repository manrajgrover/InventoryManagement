package inventorymanagement.dao;

import inventorymanagement.entities.History;

public interface HistoryDaoInterface {

  public void save(History history);

  public void update(History history);

  public History getById(int id);

}
