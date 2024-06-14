package org.curso.automacao.modulos.erp.customerservice.impl;

import java.util.List;

import org.curso.automacao.modulos.erp.customerservice.common.BaseController;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController extends BaseController<CustomerService, JpaRepository<Customer,Long>, Customer>{

	@Autowired
	private CustomerService customerService;
	
	@Override
	@GetMapping("/all")
	public final ResponseEntity<List<Customer>> findAll() {
		return super.findAll();
	}
	
	@GetMapping("/countries/all")
	public final ResponseEntity<List<String>> findAllCountries(){	
		return new ResponseEntity<>(customerService.getAllCountries(), HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/find-by/id/{id}")
	public ResponseEntity<Customer> findById(@PathVariable("id") long id){
		return super.findById(id);
	}

	@Override
	@PutMapping("/save")
	public ResponseEntity<Customer> save(@RequestBody @Validated Customer entity) {
		return super.save(entity);
	}

	@Override
	@PostMapping("/update")
	public ResponseEntity<Customer> update(@RequestBody @Validated Customer entity) {
		return super.update(entity);
	}
	
	@Override
	@DeleteMapping("/delete-by/id/{id}")
	public ResponseEntity<Customer> deleteById(@PathVariable("id") long id) {
		return super.deleteById(id);		
	}
	
}
