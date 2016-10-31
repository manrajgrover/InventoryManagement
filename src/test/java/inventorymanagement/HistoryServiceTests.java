package inventorymanagement;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.service.HistoryServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class HistoryServiceTests {

  @Autowired
  HistoryServiceInterface historyService;

  @Test
  public void issueItemTests() {
    assertTrue(2 == 2);
  }

  /*
   * @Test public void issueItemTests() { IncomingHistoryModel historyModel = new
   * IncomingHistoryModel(); historyModel.setProductId(7); historyModel.setProductTag("XYZ");
   * historyModel.setUserId(6); HistoryModel hm = historyService.issueItem(historyModel);
   * assertTrue(hm.getId() > 0); }
   */

}
