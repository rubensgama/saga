package br.gov.mj.order.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.gov.mj.saga.entity.SagaMessage;

@Entity(name = "SagaMessage")
@Table(name = "MESSAGE_RELAY", schema = "order_process")
public class OrderSagaMessage extends SagaMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5487018863264502728L;

}
