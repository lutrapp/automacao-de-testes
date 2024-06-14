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
public class Customer extends BaseEntity {
	
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
