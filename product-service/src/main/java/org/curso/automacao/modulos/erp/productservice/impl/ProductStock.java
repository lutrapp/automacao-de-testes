package org.curso.automacao.modulos.erp.productservice.impl;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.curso.automacao.modulos.erp.productservice.common.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_stocks")
@Getter @Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStock implements Serializable {

	@Id
	@Column(name = "id_product")
	@JsonIgnore
	private Long id;
	
	@Column(name="quantity")	
	@NotBlank(message="Field quantity is mandatory")
	@Min(value = 0, message = "Quantity must be at least 0.")
	private Long quantity;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id_product")
	@JsonIgnore
	private Product product;
	
}
