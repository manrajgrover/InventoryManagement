package inventorymanagement.service;

import java.util.List;

import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;

public interface ItemServiceInterface {

	public ItemModel addItem(IncomingItemModel productModel);

	public ItemModel updateItem(int id, IncomingItemModel productModel);

	public void deleteItem(int id);

	public List<ItemModel> getAllItems();

	public ItemModel getItemById(int id);

	public int getCountItem(int id);
}