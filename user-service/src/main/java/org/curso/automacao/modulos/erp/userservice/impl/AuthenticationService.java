package org.curso.automacao.modulos.erp.userservice.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.curso.automacao.modulos.erp.userservice.exceptions.UserNotFoundException;
import org.curso.automacao.modulos.erp.userservice.exceptions.UserPasswordNotMatchException;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class AuthenticationService {

	@Autowired
	UserRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserQueryBuilder userQuery;
	
	public User isLoginValid(LoginRequest loginRequest) {
		
		User user = userQuery.findUserBy(loginRequest.getUsername());
		
		if(user == null)
			throw new UserNotFoundException(loginRequest.getUsername());
		
		if(!user.getUserpass().equals(loginRequest.getPassword()))
			throw new UserPasswordNotMatchException(loginRequest.getUsername());
		
		return user;
		
	}
}
