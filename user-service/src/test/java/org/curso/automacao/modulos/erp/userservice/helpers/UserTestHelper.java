package org.curso.automacao.modulos.erp.userservice.helpers ;

import java.util.ArrayList;
import java.util.List;

import org.curso.automacao.modulos.erp.userservice.impl.User;

import com.github.javafaker.Faker;

public final class UserTestHelper {
	
	private final static Faker faker = Faker.instance();
	
	public static User createUser() {
		
		User user = User.builder().username(faker.internet().emailAddress())
								  .userpass(faker.internet().password(10, 15))
								  .name(faker.hobbit().character())
								  .roles("ROLE_USER")
								  .build();
		
		return user;
		
	}
	
	public static List<User> createUsers(){
		
		List<User> users = new ArrayList();
		
		for (int i = 0; i < 10; i ++) users.add(createUser());
		
		return users;		
	}
	

}
