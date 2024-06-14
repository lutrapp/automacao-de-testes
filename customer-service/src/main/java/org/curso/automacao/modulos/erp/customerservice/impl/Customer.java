package org.curso.automacao.modulos.erp.customerservice.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.curso.automacao.modulos.erp.customerservice.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_customers")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer extends BaseEntity {
	
	@Column(name="salary", precision = 2, nullable = false)
	@Min(value = 100, message = "Salary must be at least 100.")
	private float salary;
	
	@Column(name = "address")
	@NotBlank(message="Field address is mandatory")
	private String address;
	
	@Column(name = "email")
	@NotBlank(message="Field email is mandatory")
	private String email;
	
	@Column(name = "phone")
	@NotBlank(message="Field phone is mandatory")
	private String phoneNumber;
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zipcode")
	private String zipcode;
}
