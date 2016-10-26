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

@EnableOAuth2Sso
@RestController
@EnableWebSecurity
public class OAuthController extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = Logger.getLogger(OAuthController.class);

	@Autowired
	OAuthServiceInterface oauthService;

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests()
				.antMatchers("/", "/login**", "/node_modules/**", "/dist/**", "/src/**").permitAll().anyRequest()
				.authenticated().and().logout().logoutSuccessUrl("/").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
