package org.curso.automacao.modulos.erp.userservice.config;

import java.util.Arrays;

import org.curso.automacao.modulos.erp.userservice.impl.User;
import org.curso.automacao.modulos.erp.userservice.impl.UserQueryBuilder;
import org.curso.automacao.modulos.erp.userservice.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig implements ApplicationRunner {

	@Autowired
	private UserQueryBuilder userQuery;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment env;

	public ApplicationConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void run(ApplicationArguments args) {
		
		if (Arrays.stream(env.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("mock")))
			return;

		if (userRepository.findAll().size() == 0) {

			User admin = User.builder().name("Administrator").username("admin@automacao.org.br").userpass("password01")
					.roles("ROLE_ADMIN, ROLE_USER").build(),
					user = User.builder().name("Application User").username("user@automacao.org.br")
							.userpass("password01").roles("ROLE_USER").build();

			if (userQuery.findUserBy(admin.getUsername()) == null)
				userRepository.save(admin);

			if (userQuery.findUserBy(user.getUsername()) == null)
				userRepository.save(user);
		}
	}

}
