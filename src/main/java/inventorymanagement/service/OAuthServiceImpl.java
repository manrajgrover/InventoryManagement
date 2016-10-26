package inventorymanagement.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;
import inventorymanagement.constants.Constants;
import inventorymanagement.entities.Role;
import inventorymanagement.entities.UserRole;
import inventorymanagement.model.IncomingUserModel;
import inventorymanagement.model.LoginResponseModel;
import inventorymanagement.model.UserModel;

@Service
public class OAuthServiceImpl implements OAuthServiceInterface {

  @Autowired
  private UserServiceInterface userService;

  public LoginResponseModel authenticate(Principal principal, HttpSession session)
      throws UnauthorizedException, ForbiddenException, BadRequestException {

    if (principal == null) {
      throw new ForbiddenException("Access denied");
    }

    OAuth2Authentication oauth = (OAuth2Authentication) principal;

    if (oauth.isAuthenticated()) {
      @SuppressWarnings("unchecked")
      HashMap<String, String> details =
          (HashMap<String, String>) oauth.getUserAuthentication().getDetails();

      String domain = details.get(Constants.DOMAIN_KEY);

      System.out.println(domain);

      if (!(Constants.ALLOW_DOMAIN).equalsIgnoreCase(domain)) {
        System.out.println("Hello here!");
        throw new UnauthorizedException("Unauthorized access");
      }
      System.out.println(details);

      String email = details.get(Constants.EMAIL_KEY);
      String name = details.get(Constants.NAME_KEY);
      String contact = "9898989898";
      String picture = details.get(Constants.PICTURE_KEY);

      IncomingUserModel user = new IncomingUserModel(name, email, contact);

      UserModel userModel = userService.addUserIfNotExist(user);

      String id = Integer.toString(userModel.getId());

      session.setAttribute(Constants.SESSION_EMAIL, email);
      session.setAttribute(Constants.SESSION_NAME, name);
      session.setAttribute(Constants.SESSION_ID, id);
      session.setAttribute(Constants.SESSION_PICTURE, picture);

      Set<UserRole> roles = userModel.getRole();
      Boolean admin = false;

      for (UserRole userRole : roles) {
        Role role = userRole.getRole();
        if (role.getId() == 1) {
          admin = true;
        }
      }
      session.setAttribute(Constants.SESSION_ADMIN, admin);
      LoginResponseModel loginResponse = new LoginResponseModel();
      loginResponse.setEmail(email);
      loginResponse.setId(id);
      loginResponse.setName(name);
      loginResponse.setPicture(picture);
      loginResponse.setAdmin(admin);

      return loginResponse;
    } else {
      throw new UnauthorizedException("Login failed");
    }
  }
}
