package br.gov.mj.saga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SagaManager {
	private static Map<String, Map<String, Saga>> sagas;

	private SagaManager() {
	}
	
	public static List<String> getSagas() {
		List<String> lista = new ArrayList<>();
		if (SagaManager.sagas != null) {
			sagas.keySet().stream().forEach(key -> {
				lista.addAll(sagas.get(key).values().stream().map(saga -> saga.getId()).toList());
			});
		}
		return lista;
	}
	
	public static Saga start(String name, Step step) {
		if (SagaManager.sagas == null) {
			SagaManager.sagas = new HashMap<>();
		}
		String id = UUID.randomUUID().toString();
		SagaImpl saga = new SagaImpl(step, name);
		saga.setId(id);
		return saga;
	}
	
	public static Saga addSaga(Saga saga) {
		Map<String, Saga> mapSaga = new HashMap<>();
		if (SagaManager.sagas.containsKey(saga.getName())) {
			mapSaga = SagaManager.sagas.get(saga.getName());
		} else {
			SagaManager.sagas.put(saga.getName(), mapSaga);
		}
		mapSaga.put(saga.getId(), saga);
		return saga;
	}
	
	public static Saga get(String name, String id) {
		Saga resp = null;
		if (SagaManager.sagas != null) {
			resp = SagaManager.sagas.get(name).get(id);
		}
		return resp;
	}
}
