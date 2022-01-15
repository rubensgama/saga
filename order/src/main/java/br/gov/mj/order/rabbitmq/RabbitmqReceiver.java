package br.gov.mj.order.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.mj.saga.dto.SagaMessageDto;
import br.gov.mj.saga.event.Event;
import br.gov.mj.saga.event.SagaEventPublisher;

@Component
public class RabbitmqReceiver {
	private Logger logger = LoggerFactory.getLogger(RabbitmqReceiver.class);
	
	@Autowired
	private SagaEventPublisher publisher;
	
	@RabbitListener(queues = "#{bindingOrder.destination}")
	public void receiveMessage(SagaMessageDto msg) {
		this.logger.debug("Message received from the message broker: {}", msg);
		publisher.publish(msg, Event.IN);
	}
}
