package in.manrajsingh.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

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

import in.manrajsingh.model.LoginResponseModel;
import in.manrajsingh.service.OAuthServiceInterface;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;

@EnableOAuth2Sso
@RestController
@EnableWebSecurity
public class OAuthController extends WebSecurityConfigurerAdapter {
	
	@Autowired
	OAuthServiceInterface oauthService;

	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public LoginResponseModel user(Principal principal, HttpSession session)  throws UnauthorizedException, ForbiddenException  {
		return oauthService.authenticate(principal, session);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests()
				.antMatchers("/", "/login**", "/node_modules/**", "/dist/**", "/src/**").permitAll().anyRequest()
				.authenticated().and().logout().logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
