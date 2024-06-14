package org.curso.automacao.modulos.erp.userservice.impl;

import org.curso.automacao.modulos.erp.userservice.common.BaseService;
import org.curso.automacao.modulos.erp.userservice.enums.ServiceExceptionOperationType;
import org.curso.automacao.modulos.erp.userservice.exceptions.ServiceException;
import org.curso.automacao.modulos.erp.userservice.exceptions.UserNotFoundException;
import org.curso.automacao.modulos.erp.userservice.exceptions.UserPasswordNotMatchException;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<JpaRepository<User,Long>, User> {
	
	@Autowired
	public UserQueryBuilder userQuery;
	
	@Override
	public User save(User u) throws ServiceException {
		
		if(u.getId() == 0 && findByUserName(u.getUsername()) != null) {
			throw new ServiceException(ServiceExceptionOperationType.SAVE, "User name already exists.");
		}
		
		if(u.getUserpass().trim().length() < 10) {
			throw new ServiceException(ServiceExceptionOperationType.SAVE, "User pass is too short.");
		}
		
		return super.save(u);
	}
	
	public User delete(User u) throws ServiceException {
		
		if(u.getUsername().trim().equalsIgnoreCase("admin@automacao.org.br")) {
			throw new ServiceException(ServiceExceptionOperationType.DELETE, "User admin can not be deleted.");
		}
		
		return super.delete(u);
	}
	
	public User isLoginValid(LoginRequest loginRequest) {
		
		User user = userQuery.findUserBy(loginRequest.getUsername());
		
		if(user == null)
			throw new UserNotFoundException(loginRequest.getUsername());
		
		if(!user.getUserpass().equals(loginRequest.getPassword()))
			throw new UserPasswordNotMatchException(loginRequest.getUsername());
		
		return user;
		
	}
	
	public User findByUserName(String userName) {
		User user = userQuery.findUserBy(userName);
		return user;
	}

}
