package org.curso.automacao.modulos.erp.orderservice.impl;

import javax.annotation.PostConstruct;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("deprecation")
@Service
public class ReplicationEventListenerIntegrator {

	@Autowired
	private HibernateEntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void registerListeners() {

		EventListenerRegistry listenerRegistry = ((SessionFactoryImpl) entityManagerFactory.getSessionFactory())
				.getServiceRegistry().getService(EventListenerRegistry.class);

		//listenerRegistry.appendListeners(EventType.PRE_INSERT, ReplicationInsertEventListener.INSTANCE);

	}

}
