package org.curso.automacao.testes.helpers.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
public class OrderItem implements Serializable {
	
	private int id;
	private Long idProduct;
	private String productName;
	private float productPrice;
	private Long quantity;
	
	@JsonIdentityReference
	private Order order;
		
	@JsonIgnoreProperties
	public float getTotalItem() {
		return getProductPrice() * getQuantity();
	}
}
