package inventorymanagement.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.service.ItemServiceInterface;

@RestController
public class ItemController {

	private static final Logger LOG = Logger.getLogger(ItemController.class);

	@Autowired
	ItemServiceInterface itemService;

	@RequestMapping(value = "/items", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public ItemModel create(@RequestBody IncomingItemModel itemModel) {
		LOG.info("Request received to add an item");
		ItemModel item = itemService.addItem(itemModel);
		LOG.info("Request to add an item successful");
		return item;
	}

	@RequestMapping(value = "/items/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ItemModel update(@PathVariable int id, @RequestBody IncomingItemModel itemModel) {
		LOG.info("Request received to update an item");
		ItemModel item = itemService.updateItem(id, itemModel);
		LOG.info("Request to update an item successful");
		return item;
	}

	@RequestMapping(value = "/items/{id}/count", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public int getCount(@PathVariable int id) {
		int count = itemService.getCountItem(id);
		return count;
	}

	@RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		LOG.info("Request received to delete an item");
		itemService.deleteItem(id);
		LOG.info("Request to delete an item successful");
	}

	@RequestMapping(value = "/items", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<ItemModel> getAll() {
		LOG.info("Request received to get all items");
		List<ItemModel> itemModels = itemService.getAllItems();
		LOG.info("Request to get all items successful");
		return itemModels;
	}

	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ItemModel getById(@PathVariable int id) {
		LOG.info("Request received to get an item by id");
		ItemModel item = itemService.getItemById(id);
		LOG.info("Request to get an item by id successful");
		return item;
	}
}