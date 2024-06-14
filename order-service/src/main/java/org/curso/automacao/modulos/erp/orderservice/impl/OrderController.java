package org.curso.automacao.modulos.erp.orderservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.curso.automacao.modulos.erp.orderservice.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController extends BaseController<OrderService, JpaRepository<Order,Long>, Order>{
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	@GetMapping("/all")
	public final ResponseEntity<List<Order>> findAll() {
		return super.findAll();
	}
	
	@Override
	@GetMapping("/find-by/id/{id}")
	public ResponseEntity<Order> findById(@PathVariable("id") long id){
		return super.findById(id);
	}
	
	@Override
	@PutMapping("/save")
	public ResponseEntity<Order> save(@Validated @RequestBody Order entity) {
		return super.save(entity);
	}
	
	@Override
	@PostMapping("/update")
	public ResponseEntity<Order> update(@RequestBody @Validated Order entity) {	
		return super.update(entity);
	}

	@Override
	@DeleteMapping("/delete-by/id/{id}")
	public ResponseEntity<Order> deleteById(@PathVariable("id") long id) {
		return super.deleteById(id);		
	}
	
	@DeleteMapping("/delete-order-item-by/id/{id}/{id-item}")
	public ResponseEntity<Order> deleteById(@PathVariable("id") long id, @PathVariable("id-item") int idItem) {
		return new ResponseEntity<Order>(orderService.deleteItemById(id, idItem), HttpStatus.OK);
	}
	
}
