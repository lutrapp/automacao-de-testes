package org.curso.automacao.modulos.erp.customerservice.config;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.curso.automacao.modulos.erp.customerservice.impl.Customer;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.GameOfThrones;

public class ApplicationConfigHelper {

	@SuppressWarnings({ "unchecked", "unchecked" })
	public static List<Customer> createListOfCustomers() {
		
		Faker faker = Faker.instance();
		Hashtable<String, Customer> hobbits = new Hashtable<>();
		String hobbitName = faker.name().fullName();// .hobbit().character();
		
		while (!hobbits.containsKey(hobbitName) && hobbits.size() < 300) {			
			Address address = faker.address();

			Customer customer = Customer.builder().name(hobbitName)
												  .address(address.fullAddress())
												  .city(address.city())
												  .country(Faker.instance().country().name())
												  .zipcode(address.zipCode())
												  .state(address.state())
												  .email(Faker.instance().internet().emailAddress())
												  .company(Faker.instance().company().name())
												  .phoneNumber(faker.phoneNumber().phoneNumber())
												  .salary(faker.number().numberBetween(1000, 15000)).build();			
			
			hobbits.put(customer.getName(), customer);
			hobbitName = faker.name().fullName();// .hobbit().character();
		}
		
		return new ArrayList<>(hobbits.values());
	}
	
	public static Customer createCustomer() {

		GameOfThrones got = Faker.instance().gameOfThrones();
		Address address = Faker.instance().address();
		
		return Customer.builder()
						.name(got.character())
						.address(address.fullAddress())
						.city(address.city())
						.country(Faker.instance().country().name())
						.zipcode(address.zipCode())
						.email(Faker.instance().internet().emailAddress())
						.state(address.state())
						.company(Faker.instance().company().name())
						.phoneNumber(Faker.instance().phoneNumber().phoneNumber())
						.salary(Faker.instance().number().numberBetween(1000, 15000)).build();
		
	}

}
