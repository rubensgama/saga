package br.com.rmg.order.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.rmg.order.rabbitmq.RabbitmqSender;
import br.com.rmg.order.service.OrderService;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.event.Event;
import br.com.rmg.saga.event.SagaEvent;
import br.com.rmg.saga.event.SagaEventPublisher;
import br.com.rmg.saga.event.SagaInEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SagaEventListener implements ApplicationListener<SagaEvent> {
	@Autowired
	private RabbitmqSender<SagaMessageDto> sender;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SagaEventPublisher publisher;
	
	@Override
	public void onApplicationEvent(SagaEvent event) {
		log.debug("Executing MessageProcessorOut");
		SagaMessageDto message = (SagaMessageDto) event.getSource();
		if (event instanceof SagaInEvent) {
			SagaMessageDto dto = orderService.process(message);
			publisher.publish(dto, Event.OUT);
		} else {
			this.sender.send(message);
		}
	}
}
