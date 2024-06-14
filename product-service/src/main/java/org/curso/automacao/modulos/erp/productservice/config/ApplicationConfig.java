package org.curso.automacao.modulos.erp.productservice.config;

import java.util.Arrays;
import java.util.List;

import org.curso.automacao.modulos.erp.productservice.impl.Product;
import org.curso.automacao.modulos.erp.productservice.impl.ProductQueryBuilder;
import org.curso.automacao.modulos.erp.productservice.impl.ProductRepository;
import org.curso.automacao.modulos.erp.productservice.impl.ProductStock;
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
	private ProductRepository productRepository;

	@Autowired
	private ProductQueryBuilder productQueryBuilder;
	
	@Autowired
	private Environment env;

	public ApplicationConfig(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void run(ApplicationArguments args) {
		
		if (Arrays.stream(env.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("mock")))
			return;

		if (productRepository.findAll().size() == 0) {
			List<Product> products = ApplicationConfigHelper.createListOfProducts();
			LOGGER.info(
					"Total products to be created {" + (products != null ? String.valueOf(products.size()) : 0) + "}");

			if (products != null) {
				for (Product product : products) {

					if (productQueryBuilder.findProductBy(product.getName()) == null) {
						product.getStock().setProduct(product);
						//productStock.setProduct(product);
						productRepository.save(product);
					}
				}
			}
		}
	}

}
