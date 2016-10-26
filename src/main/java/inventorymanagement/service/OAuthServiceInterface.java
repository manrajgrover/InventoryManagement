package inventorymanagement.service;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.ForbiddenException;
import inti.ws.spring.exception.client.UnauthorizedException;
import inventorymanagement.model.LoginResponseModel;

public interface OAuthServiceInterface {

	public LoginResponseModel authenticate(Principal principal, HttpSession session)
			throws UnauthorizedException, ForbiddenException, BadRequestException;

}