package org.curso.automacao.modulos.erp.orderservice.config;

import java.util.Arrays;
import java.util.List;

import org.curso.automacao.modulos.erp.orderservice.impl.Order;
import org.curso.automacao.modulos.erp.orderservice.impl.OrderQueryBuilder;
import org.curso.automacao.modulos.erp.orderservice.impl.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Profile("!unittest")
public class ApplicationConfig implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderQueryBuilder orderQueryBuilder;

	@Autowired
	private Environment env;

	@Autowired
	ApplicationConfigHelper appHelper;

	public ApplicationConfig(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void run(ApplicationArguments args) {

		if (Arrays.stream(env.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("mock")))
			return;
		
		if (orderRepository.findAll().size() == 0) {

			List<Order> orders = appHelper.createListOfOrders();

			LOGGER.info("Total orders to be created {" + (orders != null ? String.valueOf(orders.size()) : 0) + "}");

			if (orders != null) {
				for (Order order : orders) {

					if (orderQueryBuilder.findProductBy(order.getName()) == null)
						orderRepository.save(order);
				}
			}
		}
	}

}
