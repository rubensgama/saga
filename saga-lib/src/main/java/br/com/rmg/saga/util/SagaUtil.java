package br.com.rmg.saga.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class SagaUtil {
	private static ObjectMapper mapper;
	
	private SagaUtil() {
	}
	
	public static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.registerModule(new JavaTimeModule());
			//mapper.findAndRegisterModules();
		}
		return mapper;
	}
}
