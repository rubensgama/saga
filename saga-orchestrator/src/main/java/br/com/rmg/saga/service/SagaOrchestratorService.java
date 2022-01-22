package br.com.rmg.saga.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rmg.saga.Saga;
import br.com.rmg.saga.SagaManager;
import br.com.rmg.saga.Step;
import br.com.rmg.saga.Util;
import br.com.rmg.saga.dto.OrderDto;
import br.com.rmg.saga.dto.PaymentDto;
import br.com.rmg.saga.dto.SagaDto;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.dto.SagaStatusDto;
import br.com.rmg.saga.entity.SagaMessage;
import br.com.rmg.saga.entity.SagaMessageImpl;
import br.com.rmg.saga.enums.Status;
import br.com.rmg.saga.event.Event;
import br.com.rmg.saga.event.SagaEventPublisher;
import br.com.rmg.saga.exception.SagaException;
import br.com.rmg.saga.service.SagaService;
import br.com.rmg.saga.util.SagaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SagaOrchestratorService {
	private static final String ERRO_GERAL = "Erro ao obter dto.";
	@Autowired
	private SagaEventPublisher messageProcessor;
	
	@Autowired
	private SagaService sagaService;
	
	public Saga novoSaga(String name, String idSaga) {
		Saga saga = SagaManager.start(name, Step.ORDER).step(Step.PAYMENT);
		if (idSaga != null) {
			saga.setId(idSaga);
		} else {
			String id = UUID.randomUUID().toString();
			saga.setId(id);
		}
		saga = SagaManager.addSaga(saga);
		if (saga == null) {
			throw new SagaException("Saga não encontrada.");	
		}
		return saga;
	}
	
	public SagaStatusDto startStep(SagaDto sagaDto) {
		Saga saga = novoSaga(sagaDto.getName(), sagaDto.getIdSaga());
		
		Step step = saga.current();
		String stepName = step.getStep();
		SagaStatusDto resp = new SagaStatusDto();
		
		//process step
		SagaMessageDto sagaMessage = new SagaMessageDto();
		sagaMessage.setTime(LocalDateTime.now());
		sagaMessage.setName(stepName);
		
		ObjectMapper mapper = SagaUtil.getMapper();
		String data;
		try {
			data = mapper.writeValueAsString(sagaDto.getOrder());
		} catch (JsonProcessingException e) {
			throw new SagaException("Erro ao ler ordem de compra.", e);
		}
		sagaMessage.setData(data);
		sagaMessage.setStatus(Status.PENDENTE);
		sagaMessage.setSaga(sagaDto.getName());
		sagaMessage.setIdSaga(saga.getId());
		SagaMessage sagaMsg = this.sagaService.save(sagaMessage, SagaMessageImpl.class);
		resp.setId(sagaMsg.getId());
		resp.setStatus(String.format("Next step: %s", step.getNext()));
		
		this.messageProcessor.publish(sagaMessage, Event.OUT);
		return resp;
	}
	
	private void processDtoExecutado(Saga saga, SagaMessageDto message, SagaMessageDto nextStep) {
		ObjectMapper mapper = SagaUtil.getMapper();
		if (message.getName().equalsIgnoreCase(Step.ORDER.getStep())) {
			try {
				OrderDto dto = mapper.readValue(message.getData(), OrderDto.class);
				PaymentDto nextDto = new PaymentDto();
				nextDto.setIdOrder(dto.getId());
				nextDto.setStatus("P");
				nextDto.setValue(dto.getValue());
				nextStep.setData(mapper.writeValueAsString(nextDto));
			} catch (JsonProcessingException e) {
				throw new SagaException(SagaOrchestratorService.ERRO_GERAL);
			}
		} else
		if (message.getName().equalsIgnoreCase(Step.PAYMENT.getStep())) {
			log.debug("Fim do saga.");
		}
	}
	
	private void processDtoCompensado(Saga saga, SagaMessageDto message, SagaMessageDto nextStep) {
		ObjectMapper mapper = SagaUtil.getMapper();
		if (saga.current().equals(Step.PAYMENT)) {
			try {
				PaymentDto dto = mapper.readValue(message.getData(), PaymentDto.class);
				OrderDto nextDto = new OrderDto();
				nextDto.setId(dto.getIdOrder());
				nextStep.setData(mapper.writeValueAsString(nextDto));
			} catch (JsonProcessingException e) {
				throw new SagaException(SagaOrchestratorService.ERRO_GERAL);
			}
		} else 
		if (saga.current().equals(Step.ORDER)) {
			nextStep.setData(message.getData());
		}
	}
	
	@Transactional
	public void nextStep(SagaMessageDto message) {
		SagaMessageDto nextMessage = null;
		if (message.getStatus().equals(Status.EXECUTADO)) {
			Saga saga = SagaManager.get(message.getSaga(), message.getIdSaga());
			SagaMessageDto nextStep = new SagaMessageDto();
			Step next = null;
			if (saga != null) {
				next = saga.next();
			}
			if (next != null) {
				processDtoExecutado(saga, message, nextStep);
				nextStep.setIdSaga(message.getIdSaga());
				nextStep.setName(next.getStep());
				nextStep.setStatus(Status.PENDENTE);
				nextStep.setSaga(message.getSaga());
				nextStep.setTime(LocalDateTime.now());
				sagaService.save(nextStep, SagaMessageImpl.class);
				nextMessage = nextStep;
			} else {
				processFinalizadoStep(message);
			}
		} else if (message.getStatus().equals(Status.FALHA)) {
			nextMessage = processFailStep(message);
		} else if (message.getStatus().equals(Status.COMPENSACAO_FINALIZADA)) {
			Saga saga = SagaManager.get(message.getSaga(), message.getIdSaga());
			SagaMessageDto previousStep = new SagaMessageDto();
			Step previous = null;
			if (saga != null) {
				previous = saga.previous();
			}
			if (previous != null) {
				processDtoCompensado(saga, message, previousStep);
				previousStep.setIdSaga(message.getIdSaga());
				previousStep.setName(previous.getStep());
				previousStep.setSaga(message.getSaga());
				previousStep.setStatus(Status.EM_COMPENSACAO);
				previousStep.setTime(LocalDateTime.now());
				sagaService.save(previousStep, SagaMessageImpl.class);
				nextMessage = previousStep;
			} else {
				processFinalizadoStep(message);
			}
		} else {
			//falha
			nextMessage = processFailStep(message);
		}
		if (nextMessage != null) {
			this.messageProcessor.publish(nextMessage, Event.OUT);
		}
	}
	
	@Transactional
	public void finishStep(SagaMessageDto message) {
		if (Util.isSagaValido(message)) {
			sagaService.save(message, SagaMessageImpl.class);
			nextStep(message);
		}
	}
	
	private SagaMessageDto processFailStep(SagaMessageDto message) {
		SagaMessageDto compensateStep = new SagaMessageDto();
		compensateStep.setData(message.getData());
		compensateStep.setName(message.getName());
		compensateStep.setSaga(message.getSaga());
		compensateStep.setStatus(Status.EM_COMPENSACAO);
		compensateStep.setTime(LocalDateTime.now());
		sagaService.save(compensateStep, SagaMessageImpl.class);
		return compensateStep;
	}
	
	private void processFinalizadoStep(SagaMessageDto message) {
		SagaMessageDto nextStep = new SagaMessageDto();
		nextStep.setIdSaga(message.getIdSaga());
		nextStep.setData(message.getData());
		nextStep.setName(message.getSaga());
		nextStep.setStatus(Status.FINALIZADO);
		nextStep.setSaga(message.getSaga());
		nextStep.setTime(LocalDateTime.now());
		sagaService.save(nextStep, SagaMessageImpl.class);
	}
}
