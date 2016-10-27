package inventorymanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.model.ItemModel;
import inventorymanagement.service.ItemServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class ItemServiceTests {
  
  @Autowired
  ItemServiceInterface itemService;
  
  @Test
  public void getAllItemsTests() {
    List<ItemModel> itemList = itemService.getAllItems();
    assertTrue(itemList.size() == 2);
    
    ItemModel item = itemList.get(0);
    assertTrue(item.getItemId() > 0);
    assertEquals(item.getName(), "iPhone");
  }
  
  @Test
  public void getItemsByIdTests() {
    ItemModel item = itemService.getItemById(7);
    assertEquals(item.getItemId(), 7);
    assertEquals(item.getName(), "iPhone");
    assertEquals(item.getCompany(), "Apple");
    assertEquals(item.getVersion(), "7");
    assertEquals(item.getTag(), "XYZ");
  }
  
  @Test
  public void getCountItemTests() {
    int count = itemService.getCountItem(1);
    assertEquals(count, 0);
    count = itemService.getCountItem(3);
    assertEquals(count, 1);
    count = itemService.getCountItem(2);
    assertEquals(count, 0);
  }
  
}
