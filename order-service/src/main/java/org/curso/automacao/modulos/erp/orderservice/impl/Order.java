package org.curso.automacao.modulos.erp.orderservice.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.curso.automacao.modulos.erp.orderservice.common.BaseEntity;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_orders")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "idcustomer")
	@NotBlank(message="Field customer id is mandatory")
	private Long idCustomer;
	
	@Column(name = "customername")
	@NotBlank(message="Field customer name is mandatory")
	private String customerName;
	
	@Column(name = "date")
	@NotBlank(message="Field date is mandatory")
	//@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	@Column(name = "deliverydate")
	@NotBlank(message="Field delivery date is mandatory")
	//@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate deliveryDate;
	
	@Formula(value = "(SELECT SUM(p.quantity * p.productprice) FROM tb_orders_items p WHERE p.idorder=id)")
	private Float total;
	
	@OneToMany(mappedBy = "order", targetEntity = OrderItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<OrderItem> items = new ArrayList<OrderItem>();
}
