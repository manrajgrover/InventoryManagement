package inventorymanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventorymanagement.constants.Constants;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.entities.Item;
import inventorymanagement.entities.Product;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.utilities.ItemServiceUtils;

@Service
public class ItemServiceImpl implements ItemServiceInterface {

	@Autowired
	ItemDaoInterface itemDaoImpl;

	@Autowired
	ItemServiceUtils itemServiceUtils;

	@Override
	public ItemModel addItem(IncomingItemModel itemModel) {

		Product product = new Product(itemModel.getProductId());
		String tag = itemModel.getProductTag();
		String available = "Yes";

		Item item = new Item(product, tag, available);
		itemDaoImpl.save(item);

		ItemModel im = new ItemModel(item, tag, Constants.ITEM_CREATED_MESSAGE);
		return im;
	}

	@Override
	public ItemModel updateItem(int id, IncomingItemModel itemModel) {
		Item item = itemDaoImpl.getById(id);

		item = itemServiceUtils.mapFromUpdateItem(itemModel, item);
		itemDaoImpl.update(item);

		ItemModel im = new ItemModel(item, Constants.ITEM_UPDATED_MESSAGE);
		return im;
	}

	@Override
	public void deleteItem(int id) {
		Item item = new Item(id);
		itemDaoImpl.delete(item);
	}

	@Override
	@Transactional
	public List<ItemModel> getAllItems() {
		List<Item> itemsList = itemDaoImpl.getAll();
		return itemServiceUtils.mapItemsToModel(itemsList);
	}

	@Override
	@Transactional
	public ItemModel getItemById(int id) {
		Item item = itemDaoImpl.getById(id);
		return itemServiceUtils.mapItemToModel(item);
	}

	@Override
	public int getCountItem(int id) {
		return itemDaoImpl.getCountByProductId(id);
	}
}
