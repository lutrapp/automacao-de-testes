package org.curso.automacao.modulos.erp.userservice.impl.entities.support;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class AuthenticationToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String token = StringUtils.EMPTY;
	
}
