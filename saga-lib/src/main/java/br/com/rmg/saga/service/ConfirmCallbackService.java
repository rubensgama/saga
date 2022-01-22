package br.com.rmg.saga.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConfirmCallbackService implements ConfirmCallback {
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (!ack) {
			log.error("Abnormal message sending!");
        } else {
        	log.info("The sender's father has received confirmation, correlationData={} ,ack={}, cause={}", correlationData.getId(), ack, cause);
        }		
	}

}
