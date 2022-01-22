package br.com.rmg.saga.event;

import java.time.Clock;

import org.springframework.context.ApplicationEvent;

public class SagaEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6645814314088995856L;
	
	/**
	 * @param source
	 * @param clock
	 */
	public SagaEvent(Object source, Clock clock) {
		super(source, clock);
	}

	/**
	 * @param source
	 */
	public SagaEvent(Object source) {
		super(source);
	}
}
