package br.com.rmg.saga;

import java.time.Duration;
import java.time.LocalDateTime;

import br.com.rmg.saga.dto.SagaMessageDto;
import br.com.rmg.saga.exception.SagaException;

public final class Util {	
	private Util() {}
	public static final boolean isSagaValido(SagaMessageDto msg) {
		String name = msg.getSaga();
		Saga saga = SagaManager.get(name, msg.getIdSaga());
		String data = msg.getData();
		if (saga == null) {
			throw new SagaException("Saga não encontrada.");
		}
		if (data == null) {
			throw new SagaException("Dados não encontrados.");
		}
		return !saga.emCompensacao();
	}
	public static boolean hasTimedOut(LocalDateTime time, int sagaTimeout) {
		return Duration.between(time, LocalDateTime.now()).toMillis() > sagaTimeout;
	}
}
