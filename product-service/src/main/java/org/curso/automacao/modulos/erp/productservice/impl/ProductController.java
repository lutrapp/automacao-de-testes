package org.curso.automacao.modulos.erp.productservice.impl;

import java.util.List;

import org.curso.automacao.modulos.erp.productservice.common.BaseController;
import org.curso.automacao.modulos.erp.productservice.impl.helpers.UpdateStockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends BaseController<ProductService, JpaRepository<Product,Long>, Product>{
	
	@Autowired
	ProductService productService;
	
	@Override
	@GetMapping("/all")
	public final ResponseEntity<List<Product>> findAll() {
		return super.findAll();
	}
	
	@Override
	@GetMapping("/find-by/id/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") long id){
		return super.findById(id);
	}
	
	@GetMapping("/find-by/product-name/{productName}")
	public ResponseEntity<Product> findByName(@PathVariable(name="productName") String productName){
		return new ResponseEntity<Product>(productService.findByProductName(productName), HttpStatus.OK);
	}

	@Override
	@PutMapping("/save")
	public ResponseEntity<Product> save(@Validated @RequestBody Product entity) {	
		
		if(entity != null) {
			if(entity.getStock() != null) {
				entity.getStock().setProduct(entity);

				if(entity.getStock().getId() == null)
					entity.getStock().setId(entity.getId());
			}
		}
		
		return super.save(entity);
	}
	
	@PutMapping("/update-stock")
	public ResponseEntity<Product> updateStock(@Validated @RequestBody UpdateStockInfo updateStockInfo) {
		return new ResponseEntity<Product>(productService.updateProductStock(updateStockInfo), HttpStatus.OK);
	}

	@Override
	@PostMapping("/update")
	public ResponseEntity<Product> update(@RequestBody @Validated Product entity) {
		return super.update(entity);
	}
	
	@Override
	@DeleteMapping("/delete-by/id/{id}")
	public ResponseEntity<Product> deleteById(@PathVariable("id") long id) {
		return super.deleteById(id);		
	}
	
}
