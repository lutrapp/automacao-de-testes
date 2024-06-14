package org.curso.automacao.testes.helpers.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
	
	private String username;
	private String userpass;
	private String roles;
	
	@Override
	public String toString() {
		return "Username: [" + username + "] - Password: [" + userpass + "]";
	}
	
}
