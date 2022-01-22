package br.com.rmg.saga.event;

import java.time.Clock;

public class SagaOutEvent extends SagaEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8514236277529043174L;

	/**
	 * @param source
	 * @param clock
	 */
	public SagaOutEvent(Object source, Clock clock) {
		super(source, clock);
	}

	/**
	 * @param source
	 */
	public SagaOutEvent(Object source) {
		super(source);
	}
}
