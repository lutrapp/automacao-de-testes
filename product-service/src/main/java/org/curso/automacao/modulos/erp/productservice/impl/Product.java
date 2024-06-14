package org.curso.automacao.modulos.erp.productservice.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.curso.automacao.modulos.erp.productservice.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_products")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseEntity {
	
	@Column(name="price", precision = 2, nullable = false)
	@Min(value = 1, message = "Salary must be at least 1.")
	private float price;
	
	@Column(name = "supplier")
	@NotBlank(message="Field supplier is mandatory")
	private String supplier;
	
	@Column(name = "manufacturer")
	@NotBlank(message="Field manufacturer is mandatory")
	private String manufacturer;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private ProductStock stock;
}
