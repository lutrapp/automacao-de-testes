package org.curso.automacao.modulos.erp.customerservice.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.curso.automacao.modulos.erp.customerservice.common.BaseEntity;
import org.curso.automacao.modulos.erp.customerservice.enums.ServiceExceptionOperationType;

import lombok.Getter;
import lombok.Setter;

public class ServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private ServiceExceptionOperationType serviceOperationType;
	
	@Getter @Setter
	private BaseEntity entity;
	
	public ServiceException (ServiceExceptionOperationType serviceOperationType, String message) {
		this(serviceOperationType, null, message, null);
	}
	
	public ServiceException (ServiceExceptionOperationType serviceOperationType, BaseEntity entity) {
		this(serviceOperationType, entity, StringUtils.EMPTY, null);
	}
	
	public ServiceException (ServiceExceptionOperationType serviceOperationType, BaseEntity entity, String message) {
		this(serviceOperationType, entity, message, null);
	}
	
	public ServiceException (ServiceExceptionOperationType serviceOperationType, BaseEntity entity, String message, Exception innerException) {
		super(message, innerException);
		
		this.serviceOperationType = serviceOperationType;
		this.entity = entity;
	}	

}
