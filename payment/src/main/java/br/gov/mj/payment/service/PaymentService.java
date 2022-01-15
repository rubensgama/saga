package br.gov.mj.payment.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.mj.payment.entity.Payment;
import br.gov.mj.payment.entity.PaymentSagaMessage;
import br.gov.mj.payment.repository.PaymentRepository;
import br.gov.mj.saga.dto.SagaMessageDto;
import br.gov.mj.saga.entity.SagaMessage;
import br.gov.mj.saga.enums.Status;
import br.gov.mj.saga.service.SagaService;
import br.gov.mj.saga.util.SagaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
	@Autowired
	private PaymentRepository repository;
	@Autowired
	private SagaService sagaService;
	
	@Transactional
	public SagaMessageDto process(SagaMessageDto sagaMessage) {
		SagaMessageDto result = null;
		ObjectMapper mapper = SagaUtil.getMapper();
		SagaMessageDto dto = new SagaMessageDto();
		try {
			Payment payment = getPayment(dto, sagaMessage);
			if (sagaMessage.getStatus().equals(Status.PENDENTE)) {
				Payment paymentResult = repository.save(payment);
				dto.setData(mapper.writeValueAsString(paymentResult));
				dto.setStatus(Status.EXECUTADO);
			} else
			if (sagaMessage.getStatus().equals(Status.EM_COMPENSACAO)) {
				repository.delete(payment);
				dto.setStatus(Status.COMPENSACAO_FINALIZADA);
				dto.setData(sagaMessage.getData());
			}
			
			SagaMessage msg = sagaService.save(dto, PaymentSagaMessage.class);
			result = mapper.convertValue(msg, SagaMessageDto.class);
		} catch (Exception e) {
			log.error("Erro ao processar order.", e);
			dto.setStatus(Status.FALHA);
			SagaMessage msg = sagaService.save(dto, PaymentSagaMessage.class);
			result = mapper.convertValue(msg, SagaMessageDto.class);
		}
		
		return result;
	}
	
	private Payment getPayment(SagaMessageDto dto, SagaMessageDto sagaMessage) throws JsonProcessingException {
		ObjectMapper mapper = SagaUtil.getMapper();
		dto.setIdSaga(sagaMessage.getIdSaga());
		dto.setName(sagaMessage.getName());
		dto.setSaga(sagaMessage.getSaga());
		dto.setTime(LocalDateTime.now());
		return mapper.readValue(sagaMessage.getData(), Payment.class);
	}
}
