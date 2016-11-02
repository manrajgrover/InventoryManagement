package inventorymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;
import inventorymanagement.constants.Constants;
import inventorymanagement.dao.ItemDaoInterface;
import inventorymanagement.entities.Item;
import inventorymanagement.model.IncomingItemModel;
import inventorymanagement.model.ItemModel;
import inventorymanagement.service.ItemServiceInterface;
import inventorymanagement.utilities.ItemServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class ItemServiceTests {

  @Autowired
  ItemServiceInterface itemService;

  @Autowired
  ItemDaoInterface itemDao;

  @Autowired
  ItemServiceUtils itemServiceUtils;

  @Test
  public void addItemTests() throws BadRequestException {
    IncomingItemModel itemModel = new IncomingItemModel();
    itemModel.setProductId(2);
    itemModel.setProductTag("RST");

    ItemModel im = itemService.addItem(itemModel);

    assertTrue(im.getItemId() > 0);
    assertEquals(im.getTag(), "RST");
    assertEquals(im.getMessage(), Constants.ITEM_CREATED_MESSAGE);
    assertEquals(im.getCompany(), "OnePlus");
    assertEquals(im.getName(), "Three");
    assertEquals(im.getVersion(), "2016");
  }

  @Test(expected = BadRequestException.class)
  public void addItemNoProductTagTests() throws BadRequestException {
    IncomingItemModel itemModel = new IncomingItemModel();
    itemModel.setProductId(2);

    ItemModel im = itemService.addItem(itemModel);
  }

  @Test
  public void updateItemTests() throws BadRequestException, NotFoundException {
    IncomingItemModel itemModel = new IncomingItemModel();
    itemModel.setProductId(1);
    itemModel.setProductTag("RST");

    ItemModel im = itemService.updateItem(7, itemModel);

    assertTrue(im.getItemId() > 0);
    assertEquals(im.getMessage(), Constants.ITEM_UPDATED_MESSAGE);
    assertEquals(im.getCompany(), "Apple");
    assertEquals(im.getName(), "MacBook");
    assertEquals(im.getVersion(), "2017");
    assertEquals(im.getTag(), "RST");
  }

  @Test(expected = BadRequestException.class)
  public void updateItemNoProductTagTests() throws BadRequestException, NotFoundException {
    IncomingItemModel itemModel = new IncomingItemModel();
    itemModel.setProductId(1);

    ItemModel im = itemService.updateItem(7, itemModel);
  }

  @Test(expected = NotFoundException.class)
  public void updateItemNoExistingIdTests() throws BadRequestException, NotFoundException {
    IncomingItemModel itemModel = new IncomingItemModel();
    itemModel.setProductId(1000);
    itemModel.setProductTag("RST");

    ItemModel im = itemService.updateItem(7, itemModel);
  }

  @Test
  public void deleteItemTests() throws BadRequestException {
    itemService.deleteItem(7);
    Item item = itemDao.getById(7);
    assertEquals(item, null);
  }

  @Test(expected = BadRequestException.class)
  public void deleteItemNoExistingIdTests() throws BadRequestException {
    itemService.deleteItem(-1);
  }

  @Test
  public void getAllItemsTests() {
    List<ItemModel> itemList = itemService.getAllItems();
    assertTrue(itemList.size() == 2);

    ItemModel item = itemList.get(0);
    assertTrue(item.getItemId() > 0);
    assertEquals(item.getName(), "iPhone");
  }

  @Test
  public void getItemsByIdTests() throws BadRequestException, NotFoundException {
    ItemModel item = itemService.getItemById(7);
    assertEquals(item.getItemId(), 7);
    assertEquals(item.getName(), "iPhone");
    assertEquals(item.getCompany(), "Apple");
    assertEquals(item.getVersion(), "7");
    assertEquals(item.getTag(), "XYZ");
  }

  @Test(expected = BadRequestException.class)
  public void getItemsByIdNoIdTests() throws BadRequestException, NotFoundException {
    ItemModel item = itemService.getItemById(-1);
  }

  @Test(expected = NotFoundException.class)
  public void getItemsByIdNoExistingIdTests() throws BadRequestException, NotFoundException {
    ItemModel item = itemService.getItemById(700);
  }

  @Test
  public void getCountItemTests() throws BadRequestException {
    int count = itemService.getCountItem(1);
    assertEquals(count, 0);
    count = itemService.getCountItem(3);
    assertEquals(count, 1);
    count = itemService.getCountItem(2);
    assertEquals(count, 0);
  }

  @Test(expected = BadRequestException.class)
  public void getCountItemNoIdTests() throws BadRequestException {
    int count = itemService.getCountItem(-1);
  }


}
