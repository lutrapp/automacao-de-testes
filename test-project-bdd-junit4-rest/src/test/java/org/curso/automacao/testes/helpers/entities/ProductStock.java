package org.curso.automacao.testes.helpers.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStock implements Serializable {

	private Long id;
	
	private Long quantity;
	@JsonIgnoreProperties("stock")
	private Product product;
	
}
