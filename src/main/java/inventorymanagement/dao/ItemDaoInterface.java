package inventorymanagement.dao;

import java.util.List;

import inventorymanagement.entities.Item;

public interface ItemDaoInterface {

	public void save(Item item);

	public void delete(Item item);

	public void update(Item item);

	public List<Item> getAll();

	public Item getById(int id);

	int getCountByProductId(int id);

	Item getByItemTag(String tag);
}