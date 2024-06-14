package org.curso.automacao.modulos.erp.orderservice.enums;

public enum ServiceExceptionOperationType {

	DELETE(1), SAVE(2), UPDATE(3), OTHER(4);

	public int serviceOperationValue;

	ServiceExceptionOperationType(int serviceOperationValue) {
		this.serviceOperationValue = serviceOperationValue;
	}

}
