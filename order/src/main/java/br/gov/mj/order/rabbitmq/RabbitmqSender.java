package br.gov.mj.order.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitmqSender<T> {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private OrderConfiguration configuration;
	
	public void send(T data) {
		rabbitTemplate.convertAndSend(configuration.exchange, configuration.sagaRoutingkey, data);
		log.debug("dados: " + data.toString());
	}
}
