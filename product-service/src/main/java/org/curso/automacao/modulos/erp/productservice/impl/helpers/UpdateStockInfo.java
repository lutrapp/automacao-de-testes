package org.curso.automacao.modulos.erp.productservice.impl.helpers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateStockInfo implements Serializable {

	private Long id;
	private Long quantity;
}
