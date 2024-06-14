package org.curso.automacao.modulos.erp.productservice.impl;

import java.util.Optional;

import org.curso.automacao.modulos.erp.productservice.common.BaseService;
import org.curso.automacao.modulos.erp.productservice.impl.helpers.UpdateStockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<JpaRepository<Product,Long>, Product> {

	@Autowired
	private ProductQueryBuilder productQuery;
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product findByProductName(String productName) {
		Product product = productQuery.findProductBy(productName);
		return product;
	}
	
	public Product updateProductStock(UpdateStockInfo updateStockInfo) {
		
		Optional<Product> product = productRepository.findById(updateStockInfo.getId());
		
		if(product.isPresent() &&  !product.isEmpty()) {			
			if(product.get().getStock() == null) {
				product.get().setStock(new ProductStock());
			}
			
			product.get().getStock().setQuantity(updateStockInfo.getQuantity());
			return productRepository.save(product.get());				
		}
		
		return null;
	}
}
