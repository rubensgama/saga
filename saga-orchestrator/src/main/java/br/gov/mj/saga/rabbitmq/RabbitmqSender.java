package br.gov.mj.saga.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.mj.saga.SagaConfiguration;
import br.gov.mj.saga.Step;
import br.gov.mj.saga.dto.SagaMessageDto;
import br.gov.mj.saga.service.ConfirmCallbackService;
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
