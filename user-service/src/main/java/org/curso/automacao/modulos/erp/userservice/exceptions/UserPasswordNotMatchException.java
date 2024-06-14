package org.curso.automacao.modulos.erp.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserPasswordNotMatchException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	public UserPasswordNotMatchException(String username) {
		super("Password for user {" + username +"} not match.");
		
		this.username = username;
	}
	

}
