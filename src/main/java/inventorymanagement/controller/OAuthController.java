package inventorymanagement.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;
import inventorymanagement.model.LoginResponseModel;
import inventorymanagement.service.OAuthServiceInterface;

/**
 * Controller that handles requests for Authentication using Google OAuth
 * 
 * @author manrajsingh
 *
 */
@EnableOAuth2Sso
@RestController
@EnableWebSecurity
public class OAuthController extends WebSecurityConfigurerAdapter {
  
  /**
   * {@link Logger}
   */
  private static final Logger LOG = Logger.getLogger(OAuthController.class);
  
  /**
   * {@link OAuthServiceInterface}
   */
  @Autowired
  OAuthServiceInterface oauthService;
  
  /**
   * Controller method that handles post authentication which adds user to the database, if not added
   * and returns response with important session details
   * 
   * @param principal OAuthResponse from Google OAuth
   * @param session {@link HttpSession} to set session attributes
   * @return {@link LoginResponseModel}
   * @throws UnauthorizedException Thrown when user is not authorized
   * @throws ForbiddenException  Thrown when no response is received from Google OAuth
   * @throws BadRequestException Thrown when any of the field is empty
   */
  @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public LoginResponseModel user(Principal principal, HttpSession session)
      throws UnauthorizedException, ForbiddenException, BadRequestException {
    LOG.info("Request received for user details");
    LoginResponseModel loginResponse = oauthService.authenticate(principal, session);
    LOG.info("Request for user details successful");
    return loginResponse;
  }
  
  /**
   * Spring OAuth configuration method
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests()
        .antMatchers("/", "/login**", "/node_modules/**", "/dist/**", "/src/**").permitAll()
        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/")
        .invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll().and().csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }
}
