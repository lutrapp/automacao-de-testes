package org.curso.automacao.modulos.erp.orderservice.impl.helpers;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProductInfo {

	private long id;
	private String name;
	private boolean status = true;	
	private float price;
	private String supplier;
	private String manufacturer;
	private ProductStockInfo stock;
}
