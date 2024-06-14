package org.curso.automacao.modulos.erp.orderservice.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Component
public class OrderQueryBuilder {

	@PersistenceContext
	private EntityManager em;
	
	public Order findProductBy(String name) {
//		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//		
//		QProduct product = QProduct.product;	
//		Order c = queryFactory.selectFrom(product)
//					.where(product.name.eq(name))								
//					.fetchFirst();
//		
//		return c;
		
		return null;
	}
}
