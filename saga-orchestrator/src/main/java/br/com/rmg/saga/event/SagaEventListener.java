package br.com.rmg.saga.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rmg.saga.Util;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.rabbitmq.RabbitmqSender;
import br.com.rmg.saga.service.SagaOrchestratorService;
import br.com.rmg.saga.util.SagaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SagaEventListener implements ApplicationListener<SagaEvent> {
	@Autowired
	private RabbitmqSender<SagaMessageDto> sender;
	@Autowired
	private SagaOrchestratorService sagaService;
	
	@Override
	public void onApplicationEvent(SagaEvent event) {
		log.debug("Executing MessageProcessorOut");
		SagaMessageDto message = (SagaMessageDto) event.getSource();
		if (event instanceof SagaOutEvent) {
			Util.isSagaValido(message);
			ObjectMapper mapper = SagaUtil.getMapper();
			SagaMessageDto dto = mapper.convertValue(message, SagaMessageDto.class);
			this.sender.send(dto);
		} else {
			sagaService.finishStep(message);
		}
	}
}
