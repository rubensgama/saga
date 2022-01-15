package br.gov.mj.payment.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.gov.mj.saga.entity.SagaMessage;

@Entity(name = "SagaMessage")
@Table(name = "MESSAGE_RELAY", schema = "payment")
public class PaymentSagaMessage extends SagaMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061533181023286286L;

}
