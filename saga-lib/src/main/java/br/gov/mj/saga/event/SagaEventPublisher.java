package br.gov.mj.saga.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import br.gov.mj.saga.dto.SagaMessageDto;

@Component
public class SagaEventPublisher {
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public void publish(SagaMessageDto message, Event type) {
		SagaEvent event = null;
		if (type.equals(Event.IN)) {
			event = new SagaInEvent(message);
		} else {
			event = new SagaOutEvent(message);
		}
		publisher.publishEvent(event);
	}
}
