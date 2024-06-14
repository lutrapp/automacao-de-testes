package org.curso.automacao.modulos.erp.customerservice.impl;

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
public class CustomerQueryBuilder {

	@PersistenceContext
	private EntityManager em;

	public Customer findCustomerBy(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);

		cq.select(root)
				.where(cb.equal(root.get("name"), name));

		TypedQuery<Customer> query = em.createQuery(cq);
		List<Customer> results = query.getResultList();

		return results.isEmpty() ? null : results.get(0);
	}
}
