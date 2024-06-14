package org.curso.automacao.modulos.erp.orderservice.security.helpers;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String token = StringUtils.EMPTY;
	
}
