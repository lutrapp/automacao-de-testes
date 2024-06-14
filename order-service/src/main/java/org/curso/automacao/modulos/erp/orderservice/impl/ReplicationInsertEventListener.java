package org.curso.automacao.modulos.erp.orderservice.impl;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

@SuppressWarnings("serial")
public class ReplicationInsertEventListener implements PreInsertEventListener {

	public static final ReplicationInsertEventListener INSTANCE = new ReplicationInsertEventListener();

	@Override
	public boolean onPreInsert(PreInsertEvent event) {

//		if (event.getEntity() instanceof OrderItem) {
//
//			OrderItem item = (OrderItem) event.getEntity();
//			
//		
//			event.getSession().createNativeQuery(
//					"insert into tb_orders_items key(id, idorder) values (idproduct, productname, productprice, quantity, idorder, id) values (:idproduct, :productname, :productprice, :quantity, :idorder, :id)"
//							+ " where not exists (select * from tb_order_items where id=:id and idorder=:idorder)")
//					.setParameter("idproduct", item.getIdProduct()).setParameter("productname", item.getProductName())
//					.setParameter("productprice", item.getProductPrice()).setParameter("quantity", item.getQuantity())
//					.setParameter("idorder", item.getOrder().getId()).setParameter("id", item.getId());
//		}
		
		return true;
	}

}
