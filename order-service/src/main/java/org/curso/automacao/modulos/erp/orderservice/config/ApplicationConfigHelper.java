package org.curso.automacao.modulos.erp.orderservice.config;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.curso.automacao.modulos.erp.orderservice.impl.Order;
import org.curso.automacao.modulos.erp.orderservice.impl.OrderItem;
import org.curso.automacao.modulos.erp.orderservice.impl.OrderItemKey;
import org.curso.automacao.modulos.erp.orderservice.impl.OrderService;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.CustomerInfo;
import org.curso.automacao.modulos.erp.orderservice.impl.helpers.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class ApplicationConfigHelper {
	
	@Autowired
	OrderService orderService;
	
	public Order createOrder() {
		
		Faker faker = Faker.instance();
		Order order = null;
		
		List<CustomerInfo> customers = orderService.getAllCustomers();
		List<ProductInfo> products = orderService.getAllProducts();
		
		CustomerInfo customer = null;
		ProductInfo product = null;
		
		List<OrderItem> orderItems = new ArrayList();
	
		if(customers != null && customers.size() > 0) {
				customer = customers.get(faker.number().numberBetween(0, customers.size()-1));
			
			order = Order.builder()
					.customerName(customer.getName())
					.idCustomer(customer.getId())
					.date(LocalDate.now())
					.deliveryDate(LocalDate.now().plus(5, ChronoUnit.DAYS))
					.items(orderItems)
					.build();
		
			for (int j=1; j < 6; j++) {
				
				if(products != null && products.size() > 0)
					product = products.get(faker.number().numberBetween(0, products.size()-1));
					
				OrderItem orderItem = OrderItem.builder()
													.id(OrderItemKey.builder().id(j).build())													.idProduct(product.getId())
													.productName(product.getName())
													.productPrice(product.getPrice())
													.quantity((long)faker
																.number()
																.numberBetween(1, 10))
													.build();
										
				orderItem.setOrder(order);
				//orderItems.add(orderItem);	
			}						
		}
		
		return order;	
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<Order> createListOfOrders() {
		
		Faker faker = Faker.instance();
		
		List<Order> orders = new ArrayList<Order>();
		List<CustomerInfo> customers = orderService.getAllCustomers();
		List<ProductInfo> products = orderService.getAllProducts();
		CustomerInfo customer = null;
		ProductInfo product = null;
		
		for (int i=0; i < 10; i++) {
			
			List<OrderItem> orderItems = new ArrayList();
		
			if(customers != null && customers.size() > 0)
				customer = customers.get(faker.number().numberBetween(0, customers.size()-1));
	
			Order order = Order.builder()
					.customerName(customer.getName())
					.idCustomer(customer.getId())
					.date(LocalDate.now())
					.deliveryDate(LocalDate.now().plus(5, ChronoUnit.DAYS))
					.items(orderItems)
					.build();
			
			for (int j=1; j < 6; j++) {
				
				if(products != null && products.size() > 0)
					product = products.get(faker.number().numberBetween(0, products.size()-1));
					
				OrderItem orderItem = OrderItem.builder()
													.id(OrderItemKey.builder().id(j).build())		
													.productName(product.getName())
													.productPrice(product.getPrice())
													.quantity(faker
																.number()
																.numberBetween(1, product.getStock().getQuantity() -1))
													.build();
									
				orderItem.setOrder(order);
				orderItems.add(orderItem);
			}
									
			orders.add(order);
		}
		
		return orders;		
	}
}
