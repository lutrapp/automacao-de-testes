package org.curso.automacao.modulos.erp.orderservice.security.helpers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginRequest {

	@NotNull
	@NotEmpty
	@NotBlank
	private String username;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String password;
	
}

