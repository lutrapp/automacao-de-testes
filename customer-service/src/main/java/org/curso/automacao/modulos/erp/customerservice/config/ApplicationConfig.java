package org.curso.automacao.modulos.erp.customerservice.config;

import java.util.Arrays;
import java.util.List;

import org.curso.automacao.modulos.erp.customerservice.impl.Customer;
import org.curso.automacao.modulos.erp.customerservice.impl.CustomerQueryBuilder;
import org.curso.automacao.modulos.erp.customerservice.impl.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerQueryBuilder customerQueryBuilder;
	
	@Autowired
	private Environment env;

	public ApplicationConfig(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void run(ApplicationArguments args) {
		
		if (Arrays.stream(env.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("mock")))
			return;

		if (customerRepository.findAll().size() == 0) {
			List<Customer> customers = ApplicationConfigHelper.createListOfCustomers();
			LOGGER.info("Total customers to be created {" + (customers != null ? String.valueOf(customers.size()) : 0)
					+ "}");

			if (customers != null) {
				for (Customer customer : customers) {

					if (customerQueryBuilder.findCustomerBy(customer.getName()) == null)
						customerRepository.save(customer);
				}
			}
		}

	}

}
