package org.curso.automacao.modulos.erp.productservice.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.curso.automacao.modulos.erp.productservice.impl.Product;
import org.curso.automacao.modulos.erp.productservice.impl.ProductStock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.GameOfThrones;

public class ApplicationConfigHelper {

	@SuppressWarnings({ "unchecked", "unchecked" })
	public static List<Product> createListOfProducts() {
		
		Faker faker = Faker.instance();
		Hashtable<String, Product> products = new Hashtable<String, Product>();
		String productName = faker.commerce().productName();
		List<String> companies = getCompanies();
		
		while (!products.containsKey(productName)) {			
			Address address = faker.address();

			Product product = Product.builder().name(productName)
												.manufacturer(companies.get(faker.number().numberBetween(0, companies.size()-1))) 
												.supplier(companies.get(faker.number().numberBetween(0, companies.size()-1)))
												.price(faker.number().numberBetween(1000, 15000))
												.stock(
														ProductStock
															.builder()
																.quantity((long) faker.number().numberBetween(1, 2000))
																.build()
														)												
												.build();			
			
			products.put(product.getName(), product);
			productName = faker.commerce().productName();
		}
		
		return new ArrayList<Product>(products.values());
	}
	
	public static Product createProduct() {

		Faker faker = Faker.instance();
		String productName = faker.commerce().productName();
		
		Product product = Product.builder()
										.name(productName)
										.manufacturer(faker.company().name())
										.supplier(faker.company().name())
										.price(faker.number().numberBetween(1000, 15000)).build();			
		
		return product;
		
	}
	
	private static List<String> getCompanies() {

		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("data/companies.json")) {
			ObjectMapper mapper = new ObjectMapper();
			String[] companies = mapper.readValue(inputStream, String[].class);
			return List.of(companies);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
