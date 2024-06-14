package org.curso.automacao.testes.helpers.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private String name;

	@Builder.Default
	private boolean status = true;
	
}
