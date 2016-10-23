package in.manrajsingh.service;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import in.manrajsingh.model.LoginResponseModel;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;

public interface OAuthServiceInterface {
	
	public LoginResponseModel authenticate(Principal principal, HttpSession session) throws UnauthorizedException, ForbiddenException;
	
}
