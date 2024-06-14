package org.curso.automacao.modulos.erp.userservice.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.curso.automacao.modulos.erp.userservice.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
	
	@Column(name="username", nullable = false, unique = true)
	private String username;

	@Column(name="userpass", nullable = false)
	private String userpass;
	
	@Column(name="roles", nullable = false)
	private String roles;
}
