package org.curso.automacao.modulos.erp.userservice.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserQueryBuilder {

	@PersistenceContext
	private EntityManager em;

	public User findUserBy(String userName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.select(root)
				.where(cb.equal(root.get("username"), userName));

		TypedQuery<User> query = em.createQuery(cq);
		List<User> results = query.getResultList();

		return results.isEmpty() ? null : results.get(0);
	}

	public User findUserBy(String userName, String userPassword) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		cq.select(root)
				.where(cb.and(
						cb.equal(root.get("username"), userName),
						cb.equal(root.get("userpass"), userPassword)
				));

		TypedQuery<User> query = em.createQuery(cq);
		List<User> results = query.getResultList();

		return results.isEmpty() ? null : results.get(0);
	}
}
