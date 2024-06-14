package org.curso.automacao.modulos.erp.orderservice.common;

import java.util.List;
import java.util.Optional;

import org.curso.automacao.modulos.erp.orderservice.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController<S extends BaseService<R, E>, R extends JpaRepository<E, Long>, E extends BaseEntity> {

	@Autowired
	S baseService;

	public ResponseEntity<List<E>> findAll() {

		try {
			List<E> results = baseService.findAll();

			if (!results.isEmpty())
				return new ResponseEntity<List<E>>(results, HttpStatus.OK);
			else
				return new ResponseEntity<List<E>>(HttpStatus.NOT_FOUND);
		} catch (ServiceException e) {
			return new ResponseEntity<List<E>>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	public ResponseEntity<E> findById(long id) {

		try {
			Optional<E> entity = baseService.findById(id);

			if (entity.isPresent())
				return new ResponseEntity<E>(entity.get(), HttpStatus.OK);
			else
				return new ResponseEntity<E>(HttpStatus.NOT_FOUND);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	public ResponseEntity<E> save(E entity) {
		try {
			return new ResponseEntity<E>(baseService.save(entity), HttpStatus.CREATED);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<E> deleteById(long id) {
		try {
			E entity = baseService.deleteById(id);

			if (entity != null)
				return new ResponseEntity<E>(entity, HttpStatus.OK);
			else
				return new ResponseEntity<E>(HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<E> update(E entity) {
		try {
			return new ResponseEntity<E>(baseService.update(entity), HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
