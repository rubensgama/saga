package br.com.rmg.saga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.entity.SagaMessage;
import br.com.rmg.saga.relay.repository.SagaMessageRepository;
import br.com.rmg.saga.util.SagaUtil;

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
