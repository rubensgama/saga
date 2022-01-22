package br.com.rmg.saga.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rmg.saga.SagaConfiguration;
import br.com.rmg.saga.Step;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.service.ConfirmCallbackService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitmqSender<T> {
	
	@Autowired
	protected RabbitTemplate rabbitTemplate;
	
	@Autowired
	public SagaConfiguration configuration;
	
	@Autowired
	public ConfirmCallbackService callbackService;
	
	@PostConstruct
	private void configPublisherConfirms() {
		//this.rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);
		//this.rabbitTemplate.setMandatory(true);
		this.rabbitTemplate.setConfirmCallback(callbackService);
	}
	
	public void send(SagaMessageDto data) {
		log.debug("Sending message: {}", data);
		rabbitTemplate.convertAndSend(configuration.sagaExchange, configuration.getRoutingKey(Step.valueOf(data.getName())), data);
	}
}
