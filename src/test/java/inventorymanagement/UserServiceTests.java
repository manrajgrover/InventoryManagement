package inventorymanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;
import inventorymanagement.utilities.UserServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class UserServiceTests {

  @Autowired
  UserServiceInterface userService;

  @Autowired
  UserServiceUtils userServiceUtils;

  @Test
  public void getAllUsersTests() {
    List<UserModel> users = userService.getAllUsers();

    assertEquals(users.size(), 1);

    UserModel user = users.get(0);
    String name = user.getName();
    String email = user.getEmail();
    System.out.println(name);
    System.out.println(email);

    assertEquals(name, "Manraj Singh Grover");
    assertEquals(email, "manraj.singh@practo.com");
  }

}
