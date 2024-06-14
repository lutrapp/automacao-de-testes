package org.curso.automacao.modulos.erp.orderservice.impl;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderItemKey implements Serializable {

	private Long idOrder;
	private int id;
}
