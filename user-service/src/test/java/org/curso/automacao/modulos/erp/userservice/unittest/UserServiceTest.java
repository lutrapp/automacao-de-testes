package org.curso.automacao.modulos.erp.userservice.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.curso.automacao.modulos.erp.userservice.exceptions.ServiceException;
import org.curso.automacao.modulos.erp.userservice.impl.User;
import org.curso.automacao.modulos.erp.userservice.impl.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.javafaker.Faker;

public class UserServiceTest extends BaseTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired
	private UserService userService;
	
	@Test
	public void validateFindAllUsers() throws ServiceException {
		
		LOGGER.info("Starting test of finding all users.");
		
		assertTrue(userService.findAll().size() > 0, "Validate if the service has users.");
		
		LOGGER.info("Total of users found [" + userService.findAll().size() + "].");
		
		LOGGER.info("End of test of finding all users.");
	}
	
	@Test
	public void validateUserCreation() throws ServiceException {
		
		LOGGER.info("Starting test of user creation.");
		
		User user = User.builder()
						.name(Faker.instance().name().fullName())
						.roles("ROLE_USER")
						.username(Faker.instance().internet().emailAddress())
						.userpass(Faker.instance().internet().password(10, 15))
						.build(),
			
						userCreated = userService.save(user);
		
		assertNotNull(userCreated);
		assertNotEquals(userCreated.getId(), 0, "Validate if the new user has a valid id.");
		
		LOGGER.info("New user name [" + userCreated.getName() + "]");
		LOGGER.info("New user id [" + userCreated.getId() + "]");
		LOGGER.info("New user email[" + userCreated.getUsername() + "]");
		
		LOGGER.info("End of test of user creation.");
		
	}
	
	
	@Test
	public void validateUserUpdate() throws ServiceException {
		
		LOGGER.info("Starting test of user update.");
		
		validateUserCreation();
		
		User userToUpdate = userService.findAll().get(2);
		
		userToUpdate.setUsername(Faker.instance().internet().emailAddress());
		
		userService.save(userToUpdate);
		
		assertEquals(userService.findById(userToUpdate.getId()).get().getUsername(), userToUpdate.getUsername(), "Validate if the username was updated.");
		
		LOGGER.info("End of test of user update.");
	}
	
	@Test
	public void validateUserDelete() throws ServiceException {
		
		LOGGER.info("Starting test of user update.");
		
		validateUserCreation();
		
		User userToDelete = userService.findAll().get(2);
		
		userService.delete(userToDelete);
		
		assertTrue(userService.findById(userToDelete.getId()).isEmpty(), "Validate if the user was deleted.");	
		
		LOGGER.info("End of test of user update.");
		
	}
	
}
