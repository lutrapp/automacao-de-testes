package org.curso.automacao.modulos.erp.productservice.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass 
@Getter @Setter
@AllArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="id")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private long id;
	
	@Column(name = "name")
	@NotBlank(message="Field name is mandatory.")
	private String name;

	@Column(name = "status")
	@Builder.Default
	private boolean status = true;
	
	public BaseEntity() {	
		
	}

}
