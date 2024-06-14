package org.curso.automacao.modulos.erp.orderservice.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.modulos.erp.orderservice.common.BaseService;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.ConnectionHelper;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.CustomerInfo;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.ProductInfo;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.RestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService<JpaRepository<Order, Long>, Order> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	public OrderQueryBuilder productQuery;

	@Autowired
	public OrderRepository orderRepository;
	
	@Autowired
	public RestHelper restHelper;
	
	@Autowired
    private Environment env;
	
	@Autowired
	private ConnectionHelper connectionHelper;

	public Order deleteItemById(long id, int itemId) {
		
		Optional<Order> order = repository.findById(id);
		
		if(order.isPresent())
		{
			Order o = order.get();
			Optional<OrderItem> oi = o.getItems().stream().filter(orderItem -> orderItem.getId().getId() == itemId).findFirst();
			
			if (oi.isPresent())
				o.getItems().remove(oi.get());
			
			repository.save(o);
		}
		
		order = repository.findById(id);		
		return order.get();
	}

	public List<CustomerInfo> getAllCustomers() {
		
		String url = StringUtils.EMPTY;
	
		if(connectionHelper.isRunningInsideDocker()) {
			url = env.getProperty("service.customers.url");
		} else {
			url = env.getProperty("localhost.service.customers.url"); 
		}
	
		LOGGER.info("Customer service URL: [" + url + "]");
	
		return List.of(restHelper.getRestObject(url + "/api/v1/customers/all", null, true, null,
				CustomerInfo[].class));
	}

	public List<ProductInfo> getAllProducts() {
		
		String url = StringUtils.EMPTY;
		
		if(connectionHelper.isRunningInsideDocker()) {
			url = env.getProperty("service.products.url");
		} else {
			url = env.getProperty("localhost.service.products.url"); 
		}

		LOGGER.info("Product service URL: [" + url + "]");

		return List.of(restHelper.getRestObject(url + "/api/v1/products/all", null, true, null,
				ProductInfo[].class));
	}

}
