package org.curso.automacao.modulos.erp.orderservice.common;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.curso.automacao.modulos.erp.orderservice.enums.ServiceExceptionOperationType;
import org.curso.automacao.modulos.erp.orderservice.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

public abstract class BaseService<R extends JpaRepository<E, Long>, E extends BaseEntity> {

	@Autowired
	public R repository;

	public Optional<E> findById(long id) throws ServiceException {
		try {
			return repository.findById(id);
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionOperationType.SAVE, null,
					"Error in find entity by id {" + id + "}", e);
		}
	}

	public List<E> findAll() throws ServiceException {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionOperationType.SAVE, null, "Error in finding all entities.", e);
		}
	}

	@Transactional
	public E save(E entity) throws ServiceException {
		try {
			return repository.save(entity);
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionOperationType.SAVE, entity, "Error in saving entity", e);
		}
	}

	public E update(E entity) throws ServiceException {

		Optional<E> entity0 = repository.findById(entity.getId());

		if (entity0.isEmpty())
			throw new ServiceException(ServiceExceptionOperationType.UPDATE, entity,
					"Error in update entity, id {" + entity.getId() + "} was not found");
		else
			return save(entity);
	}

	public E delete(E entity) throws ServiceException {
		try {
			repository.delete(entity);
			return entity;
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionOperationType.DELETE, entity, "Error in deleting entity", e);
		}
	}

	public E deleteById(long id) throws ServiceException {
		Optional<E> entity = repository.findById(id);

		if (entity.isPresent())
			try {
				repository.delete(entity.get());
				return entity.get();
			} catch (Exception e) {
				throw new ServiceException(ServiceExceptionOperationType.DELETE, entity.get(),
						"Error in deleting entity", e);
			}
		else
			throw new ServiceException(ServiceExceptionOperationType.DELETE, null,
					"Entity not found with id {" + id + "} to be deleted.");
	}
	
	public String generateFile() throws ServiceException {
		String gson = new Gson().toJson(findAll());
		File file = new File("data/" + UUID.randomUUID() + ".json");
		
		try (FileWriter fw = new FileWriter(file.getPath())){
			fw.write(gson);
			fw.flush();
		} catch (Exception e) {
			throw new ServiceException(ServiceExceptionOperationType.OTHER, "Error on generate file");
		}
		
		return file.getPath();
	}
}
