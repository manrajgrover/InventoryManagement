package inventorymanagement.service;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;
import inventorymanagement.model.LoginResponseModel;

/**
 * Service that validates User OAuth login and creates session for the same
 * 
 * @author manrajsingh
 *
 */
public interface OAuthServiceInterface {

  /**
   * Method that validates if user is authenticated. If yes, it checks if user is new and adds to
   * database if not already done.
   * 
   * @param principal {@link Principal} Object that contains OAuth response from the OAuth provider
   * @param session {@link HttpSession} Session to set attributes with user details
   * @return {@link LoginResponseModel}
   * @throws UnauthorizedException Thrown when user logs in with email not being of Practo
   * @throws ForbiddenException Thrown when no response is received from Google OAuth
   * @throws BadRequestException Thrown when details are missing from Google response
   */
  public LoginResponseModel authenticate(Principal principal, HttpSession session)
      throws UnauthorizedException, ForbiddenException, BadRequestException;

}
