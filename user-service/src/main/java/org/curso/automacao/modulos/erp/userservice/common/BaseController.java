package org.curso.automacao.modulos.erp.userservice.common;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.curso.automacao.modulos.erp.userservice.exceptions.ServiceException;
import org.curso.automacao.modulos.erp.userservice.impl.UserQueryBuilder;
import org.curso.automacao.modulos.erp.userservice.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class BaseController<S extends BaseService<R, E>, R extends JpaRepository<E, Long>, E extends BaseEntity> {

	@Autowired
	S baseService;
	
	@Autowired
	private UserQueryBuilder userQuery;
	
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
	
	public ResponseEntity<E> save(@RequestBody @Validated E entity) {
		try {
			return new ResponseEntity<E>(baseService.save(entity), HttpStatus.CREATED);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<E> deleteById(long id) {
		try {
			Optional<E> entity = baseService.findById(id);

			if (entity.isPresent())
				baseService.delete(entity.get());
			
			if(entity.isPresent() && !entity.isEmpty())
				return new ResponseEntity<E>(entity.get(), HttpStatus.OK);
			else
				return new ResponseEntity<E>(HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<E> update(@RequestBody @Validated E entity) {
		try {
			return new ResponseEntity<E>(baseService.update(entity), HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<E>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
