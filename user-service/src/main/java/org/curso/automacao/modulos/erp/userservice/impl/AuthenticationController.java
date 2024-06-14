package org.curso.automacao.modulos.erp.userservice.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.curso.automacao.modulos.erp.userservice.exceptions.UserNotFoundException;
import org.curso.automacao.modulos.erp.userservice.exceptions.UserPasswordNotMatchException;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.AuthenticationToken;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.JwtUtils;
import org.curso.automacao.modulos.erp.userservice.impl.entities.support.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	AuthenticationService service;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/auth")
	public final ResponseEntity<AuthenticationToken> authenticate(@Validated @RequestBody LoginRequest loginRequest) {
		
		User user = service.isLoginValid(loginRequest);
		
		AuthenticationToken token = jwtUtils.generateToken(new UserDetails() {
			
			private Long id;
			private String username;
			private String password;
			private String email;
			private Collection<? extends GrantedAuthority> grantedAuthorities;
			
			{
				username = user.getUsername();
				password = user.getUserpass();
				email = user.getUsername();		
				
				grantedAuthorities = user.getRoles() != null ? Arrays.stream(user.getRoles().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()) : Collections.emptyList();
			}
			
			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return username;
			}
			
			@Override
			public String getPassword() {
				return password;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return grantedAuthorities;
			}
			
		});
				
		LOGGER.info("Token generated succesfully {" + token.token + "}");
		
		return new ResponseEntity<AuthenticationToken>(token, HttpStatus.OK);
	}
	
}
