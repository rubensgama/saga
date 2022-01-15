package br.gov.mj.payment.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.gov.mj.payment.rabbitmq.RabbitmqSender;
import br.gov.mj.payment.service.PaymentService;
import br.gov.mj.saga.dto.SagaMessageDto;
import br.gov.mj.saga.event.Event;
import br.gov.mj.saga.event.SagaEvent;
import br.gov.mj.saga.event.SagaEventPublisher;
import br.gov.mj.saga.event.SagaInEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SagaEventListener implements ApplicationListener<SagaEvent> {
	@Autowired
	private RabbitmqSender<SagaMessageDto> sender;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private SagaEventPublisher publisher;
	
	@Override
	public void onApplicationEvent(SagaEvent event) {
		log.debug("Executing MessageProcessorOut");
		SagaMessageDto message = (SagaMessageDto)event.getSource();
		if (event instanceof SagaInEvent) {
			SagaMessageDto dto = paymentService.process(message);
			publisher.publish(dto, Event.OUT);
		} else {
			this.sender.send(message);
		}
	}
}
