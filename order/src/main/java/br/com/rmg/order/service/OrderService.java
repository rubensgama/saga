package br.com.rmg.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rmg.order.entity.Order;
import br.com.rmg.order.entity.OrderDetail;
import br.com.rmg.order.entity.OrderSagaMessage;
import br.com.rmg.order.repository.OrderRepository;
import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.entity.SagaMessage;
import br.com.rmg.saga.enums.Status;
import br.com.rmg.saga.service.SagaService;
import br.com.rmg.saga.util.SagaUtil;

@Service
public class OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository repository;
	@Autowired
	private SagaService sagaService;
	
	@Transactional
	public SagaMessageDto process(SagaMessageDto sagaMessage) {
		SagaMessageDto result = null;
		ObjectMapper mapper = SagaUtil.getMapper();
		SagaMessageDto dto = new SagaMessageDto();
		dto.setIdSaga(sagaMessage.getIdSaga());
		dto.setName(sagaMessage.getName());
		dto.setSaga(sagaMessage.getSaga());
		dto.setTime(LocalDateTime.now());	
		try {
			Order order = mapper.readValue(sagaMessage.getData(), Order.class);
			if (sagaMessage.getStatus().equals(Status.PENDENTE)) {
				order.getItems().forEach(detail -> {
					detail.setOrder(order);
				});
				Order orderResult = repository.save(order);
				List<OrderDetail> details = repository.findDetailsByOrder(orderResult.getId());
				orderResult.setItems(details);
				dto.setData(mapper.writeValueAsString(orderResult));	
				dto.setStatus(Status.EXECUTADO);
			} else if (sagaMessage.getStatus().equals(Status.EM_COMPENSACAO)) {
				repository.delete(order);
				dto.setStatus(Status.COMPENSACAO_FINALIZADA);
				dto.setData(sagaMessage.getData());
			}
			SagaMessage msg = sagaService.save(dto, OrderSagaMessage.class);
			result = mapper.convertValue(msg, SagaMessageDto.class);
		} catch (Exception e) {
			logger.error("Erro ao processar order.", e);
			dto.setStatus(Status.FALHA);
			SagaMessage msg = sagaService.save(dto, OrderSagaMessage.class);
			result = mapper.convertValue(msg, SagaMessageDto.class);
		}
		
		return result;
	}
}
