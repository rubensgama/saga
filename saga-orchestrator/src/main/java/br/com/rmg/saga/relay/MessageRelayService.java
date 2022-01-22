package br.com.rmg.saga.relay;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rmg.saga.Saga;
import br.com.rmg.saga.SagaConfiguration;
import br.com.rmg.saga.SagaManager;
import br.com.rmg.saga.Util;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.entity.SagaMessage;
import br.com.rmg.saga.enums.Status;
import br.com.rmg.saga.event.Event;
import br.com.rmg.saga.event.SagaEventPublisher;
import br.com.rmg.saga.relay.repository.SagaMessageRepository;
import br.com.rmg.saga.service.SagaOrchestratorService;
import br.com.rmg.saga.util.SagaUtil;

@Service
public class MessageRelayService {
	private Logger logger = LoggerFactory.getLogger(MessageRelayService.class);
	
	@Autowired
	private SagaEventPublisher processor;
	
	@Autowired
	private SagaMessageRepository repository;
	
	@Autowired
	private SagaConfiguration configuration;
	
	@Autowired
	private SagaOrchestratorService sagaService;
	
	@Scheduled(initialDelay = 60000, fixedRate = 1800000)
	public void run() {
	    logger.info("Current time is {} ", Calendar.getInstance().getTime());
	    List<SagaMessage> messages = this.repository.findLastSagaNotFinished();
	    List<SagaMessage> falhas = messages.stream().filter(msg -> msg.getStatus().equals(Status.FALHA)).toList();
	    List<SagaMessage> pendentes = messages.stream().filter(msg -> msg.getStatus().equals(Status.PENDENTE)).toList();
	    List<SagaMessage> compensados = messages.stream().filter(msg -> msg.getStatus().equals(Status.COMPENSACAO_FINALIZADA)).toList();
	    pendentes.stream().filter(msg -> Util.hasTimedOut(msg.getTime(), configuration.sagaTimeout)).forEach(msg -> compensar(msg));
	    falhas.stream().forEach(msg -> compensar(msg));
	    compensados.stream().forEach(msg -> {
	    	ObjectMapper mapper = SagaUtil.getMapper();
	    	Saga saga = verificarSaga(msg);
	    	saga.iniciaCompensacao();
	    	sagaService.nextStep(mapper.convertValue(msg, SagaMessageDto.class));
	    });
	}
	
	private Saga verificarSaga(SagaMessage msg) {
		List<String> sagas = SagaManager.getSagas();
		String idSaga = msg.getIdSaga();
		Saga saga = null;
    	if (!sagas.contains(idSaga)) {
    		saga = sagaService.novoSaga(msg.getSaga(), idSaga);
    		saga.setStep(msg.getName());
    	} else {
    		saga = SagaManager.get(msg.getName(), idSaga);
    	}
    	return saga;
	}
	
	private void compensar(SagaMessage msg) {
		ObjectMapper mapper = SagaUtil.getMapper();
		verificarSaga(msg);
    	msg.setStatus(Status.EM_COMPENSACAO);
    	processor.publish(mapper.convertValue(msg, SagaMessageDto.class), Event.OUT);
	}
}
