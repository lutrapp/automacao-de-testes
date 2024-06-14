package org.curso.automacao.testes.helpers.entities;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class AuthenticationToken implements Serializable {

	public String token = StringUtils.EMPTY;
	
}
