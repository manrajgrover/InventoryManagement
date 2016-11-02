package inventorymanagement;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import inventorymanagement.dao.UserDaoInterface;
import inventorymanagement.dao.UserRoleDaoInterface;
import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;
import inventorymanagement.utilities.UserServiceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class UserServiceTests {

  @Autowired
  UserServiceInterface userService;

  @Autowired
  UserDaoInterface userDao;

  @Autowired
  UserRoleDaoInterface userroleDao;

  @Autowired
  UserServiceUtils userServiceUtils;

  @Test
  public void getAllUsersTests() {
    List<UserModel> users = userService.getAllUsers();

    for (UserModel user : users) {
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
