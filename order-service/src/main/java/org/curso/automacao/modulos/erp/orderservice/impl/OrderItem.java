package org.curso.automacao.modulos.erp.orderservice.impl;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_orders_items")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderItem implements Serializable {
	
	//@Id
	//private int id;
	
	
	@EmbeddedId
	private OrderItemKey id;
	
	@Column(name = "idproduct")
	@NotBlank(message="Field product id is mandatory")
	private Long idProduct;
		
	@Column(name = "productname")
	@NotBlank(message="Field product name is mandatory")
	private String productName;
	
	@Column(name = "productprice")
	@NotBlank(message="Field product price is mandatory")
	private float productPrice;
	
	@Column(name = "quantity")
	@NotBlank(message="Field quantity is mandatory")
	private Long quantity;
	
	//@Id
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@MapsId("idOrder")
	@JoinColumn(name = "idorder")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference
	private Order order;

	@JsonIgnore
	public float getTotalItem() {
		return getProductPrice() * getQuantity();
	}
}
