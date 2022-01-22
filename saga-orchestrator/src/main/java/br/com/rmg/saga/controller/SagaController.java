package br.com.rmg.saga.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rmg.saga.dto.SagaDto;
import br.com.rmg.saga.dto.SagaStatusDto;
import br.com.rmg.saga.service.SagaOrchestratorService;

@RestController
public class SagaController {
	@Autowired
	private SagaOrchestratorService service;
	private Logger logger = LoggerFactory.getLogger(SagaController.class);
	
	@PostMapping("/order-processing")
	public SagaStatusDto processOrder(@RequestBody SagaDto dto) {
		logger.debug("processing order");
		SagaStatusDto status = this.service.startStep(dto);
		logger.debug("order processed");
		return status;
	}
}
