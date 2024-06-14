package org.curso.automacao.modulos.erp.userservice.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.curso.automacao.modulos.erp.userservice.exceptions.ServiceException;
import org.curso.automacao.modulos.erp.userservice.impl.User;
import org.curso.automacao.modulos.erp.userservice.impl.UserQueryBuilder;
import org.curso.automacao.modulos.erp.userservice.impl.UserRepository;
import org.curso.automacao.modulos.erp.userservice.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.javafaker.Faker;

@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@ActiveProfiles({ "dev", "mock" })
public class UserServiceMockedTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceMockedTest.class);

	private static List<User> users = new ArrayList<>();

	@MockBean
	private UserQueryBuilder userQueryBuilder;

	@MockBean
	private UserRepository userRepository;

	@InjectMocks
	@Autowired
	private UserService userService;

	public static User createUser() {
		return User.builder().name(Faker.instance().name().fullName()).roles("ROLE_USER")
				.username(Faker.instance().internet().emailAddress())
				.userpass(Faker.instance().internet().password(10, 15)).build();
	}

	@BeforeAll
	public static void setUpClass() {

		for (int i = 0; i < 10; i++) {
			users.add(createUser());
			users.get(i).setId(i + 1);
		}

	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void validateFindAllUsers() throws ServiceException {

		LOGGER.info("Starting test of finding all users.");

		// Mocking do método userService.repository.findAll
		doAnswer(invocation -> {
			return users;
		}).when(userService.repository).findAll();

		assertTrue(userService.findAll().size() > 0, "Validate if the service has users.");

		verify(userService.repository, times(1)).findAll();

		LOGGER.info("Total of users found [" + userService.findAll().size() + "].");

		LOGGER.info("End of test of finding all users.");
	}

	@Test
	public void validateUserCreation() throws ServiceException {

		LOGGER.info("Starting test of user creation.");

		// Mocking do método userService.repository.save
		doAnswer(invocation -> {

			User userToAdd = invocation.getArgument(0);

			users.add(userToAdd);
			userToAdd.setId(users.size() + 1);

			return userToAdd;

		}).when(userService.repository).save(any(User.class));
		
		// Mocking do método userService.userQuery.findUserBy
		doAnswer(invocation -> {
			
			String userName = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getUsername() == userName).findFirst();

			if(userFound.isPresent())
				return userFound.get();
			
			return null;
		}).when(userService.userQuery).findUserBy(anyString());

		User user = createUser(), userCreated = userService.save(user);

		assertNotNull(userCreated);
		assertNotEquals(userCreated.getId(), 0, "Validate if the new user has a valid id.");

		verify(userService.repository, times(1)).save(any(User.class));		
		verify(userService.userQuery, atLeastOnce()).findUserBy(anyString());	

		LOGGER.info("New user name [" + userCreated.getName() + "]");
		LOGGER.info("New user id [" + userCreated.getId() + "]");
		LOGGER.info("New user email[" + userCreated.getUsername() + "]");

		LOGGER.info("End of test of user creation.");

	}
	
	@Test
	public void validateUserCreationWithDuplicatedUserName() throws ServiceException {

		LOGGER.info("Starting test of user creation.");

		// Mocking do método userService.repository.save
		doAnswer(invocation -> {

			User userToAdd = invocation.getArgument(0);

			users.add(userToAdd);
			userToAdd.setId(users.size() + 1);

			return userToAdd;

		}).when(userService.repository).save(any(User.class));
		
		// Mocking do método userService.userQuery.findUserBy
		doAnswer(invocation -> {
			
			String userName = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getUsername() == userName).findFirst();

			if(userFound.isPresent())
				return userFound.get();
			
			return null;
		}).when(userService.userQuery).findUserBy(anyString());
		
		User user = users.get(1);
		user.setId(0);
	
		assertThrows(ServiceException.class, () -> userService.save(user), "Validate if an error is thrown with duplicated name");

		verify(userService.repository, times(0)).save(any(User.class));
		verify(userService.userQuery, times(1)).findUserBy(anyString());		

		LOGGER.info("End of test of user creation.");

	}
	
	@Test
	public void validateUserCreationWithShortUserPass() throws ServiceException {

		LOGGER.info("Starting test of user creation.");

		// Mocking do método userService.repository.save
		doAnswer(invocation -> {

			User userToAdd = invocation.getArgument(0);

			users.add(userToAdd);
			userToAdd.setId(users.size() + 1);

			return userToAdd;

		}).when(userService.repository).save(any(User.class));
		
		// Mocking do método userService.userQuery.findUserBy
		doAnswer(invocation -> {
			
			String userName = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getUsername() == userName).findFirst();

			if(userFound.isPresent())
				return userFound.get();
			
			return null;
		}).when(userService.userQuery).findUserBy(anyString());
		
		User user = createUser();
		user.setUserpass(Faker.instance().internet().password(5, 9));
	
		assertThrows(ServiceException.class, () -> userService.save(user), "Validate if an error is thrown with short password");
		
		verify(userService.repository, times(0)).save(any(User.class));
		verify(userService.userQuery, times(1)).findUserBy(anyString());		

		LOGGER.info("End of test of user creation.");

	}

	@Test
	public void validateUserUpdate() throws ServiceException {

		LOGGER.info("Starting test of user update.");

		// Mocking do método userService.repository.findAll
		doAnswer(invocation -> {
			return users;
		}).when(userService.repository).findAll();

		// Mocking do método userService.repository.save
		doAnswer(invocation -> {

			User userToAddOrUpdate = invocation.getArgument(0);

			if (userToAddOrUpdate.getId() == 0) {
				users.add(userToAddOrUpdate);
				userToAddOrUpdate.setId(users.size() + 1);
			} else {

				Optional<User> userFound = users.stream().filter(u -> u.getId() == userToAddOrUpdate.getId())
						.findFirst();

				if (userFound.isPresent()) {
					users.set(users.indexOf(userFound.get()), userToAddOrUpdate);
				}
			}

			return userToAddOrUpdate;

		}).when(userService.repository).save(any(User.class));

		// Mocking do método userService.repository.findById
		doAnswer(invocation -> {

			Long id = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getId() == id).findFirst();

			return userFound;

		}).when(userService.repository).findById(anyLong());
		
		// Mocking do método userService.userQuery.findUserBy
		doAnswer(invocation -> {
			
			String userName = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getUsername() == userName).findFirst();

			if(userFound.isPresent())
				return userFound.get();
			
			return null;
		}).when(userService.userQuery).findUserBy(anyString());

		User userToUpdate = userService.findAll().get(2);
		userToUpdate.setUsername(Faker.instance().internet().emailAddress());

		userService.save(userToUpdate);

		assertEquals(userService.findById(userToUpdate.getId()).get().getUsername(), userToUpdate.getUsername(),
				"Validate if the username was updated.");

		verify(userService.repository, times(1)).findAll();
		verify(userService.repository, times(1)).findById(anyLong());
		verify(userService.repository, times(1)).save(any(User.class));
		verify(userService.userQuery, times(0)).findUserBy(anyString());	

		LOGGER.info("End of test of user update.");
	}

	@Test
	public void validateUserDelete() throws ServiceException {

		LOGGER.info("Starting test of user update.");

		// Mocking do método userService.repository.findAll
		doAnswer(invocation -> {
			return users;
		}).when(userService.repository).findAll();

		// Mocking do método userService.repository.delete
		doAnswer(invocation -> {

			User userToDelete = invocation.getArgument(0);
			users.remove(userToDelete);

			return null;

		}).when(userService.repository).delete(any(User.class));

		// Mocking do método userService.repository.findById
		doAnswer(invocation -> {

			Long id = invocation.getArgument(0);
			Optional<User> userFound = users.stream().filter(u -> u.getId() == id).findFirst();

			return userFound;

		}).when(userService.repository).findById(anyLong());

		User userToDelete = userService.findAll().get(2);

		userService.delete(userToDelete);

		assertTrue(userService.findById(userToDelete.getId()).isEmpty(), "Validate if the user was deleted.");

		verify(userService.repository, times(1)).findAll();
		verify(userService.repository, times(1)).findById(anyLong());
		verify(userService.repository, times(1)).delete(any(User.class));

		LOGGER.info("End of test of user update.");

	}

}
