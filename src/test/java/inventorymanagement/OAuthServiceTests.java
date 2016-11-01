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

import inti.ws.spring.exception.client.BadRequestException;
import inventorymanagement.dao.UserDaoInterface;
import inventorymanagement.dao.UserRoleDaoInterface;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.UserModel;
import inventorymanagement.service.UserServiceInterface;
import inventorymanagement.utilities.OAuthServiceUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class OAuthServiceTests {
  
  @Autowired
  UserServiceInterface userService;
  
  @Autowired
  UserDaoInterface userDao;
  
  @Autowired
  UserRoleDaoInterface userroleDao;

  @Autowired
  OAuthServiceUtils oauthServiceUtils;
  
  @Test
  public void addUserIfExistTests() throws BadRequestException {
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Manraj Singh Grover");
    incomingUserModel.setEmail("manraj.singh@practo.com");
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);

    List<UserModel> users = userService.getAllUsers();
    assertEquals(users.size(), 1);
    assertEquals(oauthServiceUtils.checkIfAdmin(userModel), true);
  }

  @Test
  public void addUserIfNotExistTests() throws BadRequestException {
    
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Ayush Aggarwal");
    incomingUserModel.setEmail("ayush.aggr@practo.com");
    
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);
    List<UserModel> users = userService.getAllUsers();
    assertEquals(users.size(), 2);
  }
  
  @Test(expected = BadRequestException.class)
  public void addUserIfNotExistNoNameTests() throws BadRequestException {
    
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setEmail("ayush.aggr@practo.com");
    
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);
  }
  
  @Test(expected = BadRequestException.class)
  public void addUserIfNotExistInvalidNameTests() throws BadRequestException {
    
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("");
    incomingUserModel.setEmail("ayush.aggr@practo.com");
    
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);
  }
  
  @Test(expected = BadRequestException.class)
  public void addUserIfNotExistNoEmailTests() throws BadRequestException {
    
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Ayush Aggarwal");
    
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);
  }
  
  @Test(expected = BadRequestException.class)
  public void addUserIfNotExistInvalidEmailTests() throws BadRequestException {
    
    IncomingUserModel incomingUserModel = new IncomingUserModel();
    incomingUserModel.setName("Ayush Aggarwal");
    incomingUserModel.setEmail("");
    
    UserModel userModel = oauthServiceUtils.addUserIfNotExist(incomingUserModel);
  }
}
