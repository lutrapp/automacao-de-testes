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
public class Product extends BaseEntity {
	
	private float price;
	private String supplier;
	private String manufacturer;	
	private ProductStock stock;
	
}
