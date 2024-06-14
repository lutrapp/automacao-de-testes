package org.curso.automacao.modulos.erp.userservice.impl.entities.support;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@NoArgsConstructor
@AllArgsConstructor
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
