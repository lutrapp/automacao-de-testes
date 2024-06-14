package org.curso.automacao.modulos.erp.customerservice.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.curso.automacao.modulos.erp.customerservice.common.BaseService;
import org.curso.automacao.modulos.erp.customerservice.enums.ServiceExceptionOperationType;
import org.curso.automacao.modulos.erp.customerservice.exceptions.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

@Service
public class CustomerService extends BaseService<JpaRepository<Customer,Long>, Customer> {

	
	@Override
	public Customer save(Customer entity) throws ServiceException {
	
		if(entity.getName().trim().length() < 10) 
			throw new ServiceException(ServiceExceptionOperationType.SAVE, entity, "Field name must have at least 10 chars.");
		
		return super.save(entity);		
	}
	
	public List<String> getAllCountries(){
		
		Set<String> countries = new HashSet<>();
		Faker faker = Faker.instance();
		
		String country = faker.country().name();
		
		while(countries.size() < 193) {		
			if(!countries.contains(country))
				countries.add(country);	
			
			country = faker.country().name();
		}
		
		return countries.stream().sorted().toList();
	}
	
}
