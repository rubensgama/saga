package br.com.rmg.payment.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.event.Event;
import br.com.rmg.saga.event.SagaEventPublisher;

@Component
public class RabbitmqReceiver {
	private Logger logger = LoggerFactory.getLogger(RabbitmqReceiver.class);
	
	@Autowired
	private SagaEventPublisher publisher;
	
	@RabbitListener(queues = "#{bindingPayment.destination}")
	public void receiveMessage(SagaMessageDto msg) {
		this.logger.debug("Message received from the message broker: {}", msg);
		this.publisher.publish(msg, Event.IN);
	}
}
