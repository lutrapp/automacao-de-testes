package org.curso.automacao.modulos.erp.orderservice.impl.helpers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductStockInfo {
	
	private Long id;
	private Long quantity;
	
	@JsonIgnoreProperties(value={"stock"}, allowSetters=true)
	private ProductInfo product;

}
