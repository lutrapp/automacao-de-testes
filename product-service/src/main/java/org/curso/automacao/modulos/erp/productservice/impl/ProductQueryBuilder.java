package org.curso.automacao.modulos.erp.productservice.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

@Component
public class ProductQueryBuilder {

	@PersistenceContext
	private EntityManager em;


	public Product findProductBy(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);

		cq.select(root)
				.where(cb.equal(root.get("name"), name));

		TypedQuery<Product> query = em.createQuery(cq);
		List<Product> results = query.getResultList();

		return results.isEmpty() ? null : results.get(0);
	}
}
