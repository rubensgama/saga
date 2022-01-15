package br.gov.mj.saga.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "SagaMessage")
@Table(name = "MESSAGE_RELAY", schema = "saga")
public class SagaMessageImpl extends SagaMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1588850639733457962L;

}
