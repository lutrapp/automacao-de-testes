package org.curso.automacao.modulos.erp.orderservice.impl.helpers;

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
public class CustomerInfo {
	
	private long id;
	private String name;
	private boolean status = true;	
	private float salary;
	private String address;
	private String email;
	private String phoneNumber;
	private String company;
	private String city;
	private String state;
	private String country;
	private String zipcode;

}
