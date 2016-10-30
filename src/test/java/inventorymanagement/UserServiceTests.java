package inventorymanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;
import inventorymanagement.utilities.UserServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestComponent
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class UserServiceTests {

  @Autowired
  UserServiceInterface userService;

  @Autowired
  UserServiceUtils userServiceUtils;

  @Test
  @Rollback(true)
  public void addUserIfExistTests() throws BadRequestException {
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Manraj Singh Grover");
    incomingUserModel.setEmail("manraj.singh@practo.com");
    UserModel userModel = userService.addUserIfNotExist(incomingUserModel);
    List<UserModel> users = userService.getAllUsers();
    assertEquals(users.size(), 1);
  }
  
  @Test
  @Rollback(true)
  public void addUserIfNotExistTests() throws BadRequestException {
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Ayush Aggarwal");
    incomingUserModel.setEmail("ayush.aggr@practo.com");
    UserModel userModel = userService.addUserIfNotExist(incomingUserModel);
    List<UserModel> users = userService.getAllUsers();

    assertEquals(users.size(), 2);
  }

  @Test
  public void getAllUsersTests() {
    List<UserModel> users = userService.getAllUsers();
    
    for(UserModel user: users) {
      System.out.println(user.getEmail());
    }

    assertEquals(users.size(), 1);

    UserModel user = users.get(0);
    String name = user.getName();
    String email = user.getEmail();

    assertEquals(name, "Manraj Singh Grover");
    assertEquals(email, "manraj.singh@practo.com");
  }

}
