package br.gov.mj.saga.event;

import java.time.Clock;

public class SagaInEvent extends SagaEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2612476826275451905L;

	/**
	 * @param source
	 * @param clock
	 */
	public SagaInEvent(Object source, Clock clock) {
		super(source, clock);
	}

	/**
	 * @param source
	 */
	public SagaInEvent(Object source) {
		super(source);
	}
}
