package org.curso.automacao.testes.helpers.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity {

	private Long idCustomer;	
	private String customerName;
	private Date date;
	private Date deliveryDate;
	private float total;
	
	@JsonBackReference
	private List<OrderItem> items = new ArrayList<OrderItem>();
}
