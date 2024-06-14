package org.curso.automacao.modulos.erp.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	public UserNotFoundException(String username) {
		super("User {" + username +"} was not found.");
		
		this.username = username;
	}
	

}
