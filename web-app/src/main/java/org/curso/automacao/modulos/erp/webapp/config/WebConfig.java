package org.curso.automacao.modulos.erp.webapp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {

		// Default pages
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/index.html").setViewName("index");
		registry.addViewController("/").setViewName("index");

		// Login pages
		registry.addViewController("/login").setViewName("pages-login");
		registry.addViewController("/login.html").setViewName("pages-login");

		// Customer pages
		registry.addViewController("/customers-list").setViewName("forms-customers-list");
		registry.addViewController("/customers-list.html").setViewName("forms-customers-list");
		registry.addViewController("/customers-edit").setViewName("forms-customers-edit");
		registry.addViewController("/customers-edit.html").setViewName("forms-customers-edit");

		// Products pages
		registry.addViewController("/products-list").setViewName("forms-products-list");
		registry.addViewController("/products-list.html").setViewName("forms-products-list");
		registry.addViewController("/products-edit").setViewName("forms-products-edit");
		registry.addViewController("/products-edit.html").setViewName("forms-products-edit");

		// Products pages
		registry.addViewController("/stocks-list").setViewName("forms-stocks-list");
		registry.addViewController("/stocks-list.html").setViewName("forms-stocks-list");
		registry.addViewController("/stocks-edit").setViewName("forms-stocks-edit");
		registry.addViewController("/stocks-edit.html").setViewName("forms-stocks-edit");

		// Users pages
		registry.addViewController("/users-list").setViewName("forms-users-list");
		registry.addViewController("/users-list.html").setViewName("forms-users-list");
		registry.addViewController("/users-edit").setViewName("forms-users-edit");
		registry.addViewController("/users-edit.html").setViewName("forms-users-edit");
		
		// Orders pages
		registry.addViewController("/orders-list").setViewName("forms-orders-list");
		registry.addViewController("/orders-list.html").setViewName("forms-orders-list");
		registry.addViewController("/orders-edit").setViewName("forms-orders-edit");
		registry.addViewController("/orders-edit.html").setViewName("forms-orders-edit");
		
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
		registry.setOrder(1);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
		return container -> {
			container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
		};
	}

}
