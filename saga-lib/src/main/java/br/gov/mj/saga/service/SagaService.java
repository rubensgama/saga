package br.gov.mj.saga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.mj.saga.dto.SagaMessageDto;
import br.gov.mj.saga.entity.SagaMessage;
import br.gov.mj.saga.relay.repository.SagaMessageRepository;
import br.gov.mj.saga.util.SagaUtil;

@Service
public class SagaService {
	@Autowired
	private SagaMessageRepository repository;
	
	@Transactional
	public SagaMessage save(SagaMessageDto message, Class<?> sagaMsgType) {
		ObjectMapper mapper = SagaUtil.getMapper();
		SagaMessage sagaMessage = (SagaMessage) mapper.convertValue(message, sagaMsgType);
		return repository.save(sagaMessage);
	}
}
