package br.com.rmg.saga;

import java.util.ArrayList;
import java.util.List;

public class SagaImpl implements Saga {
	private List<Participant> saga;
	private String name;
	private String id;
	private int active;
	private boolean emCompensacao = Boolean.FALSE;
	
	protected SagaImpl(Step step, String name) {
		this.saga = new ArrayList<>();
		this.name = name;
		this.add(new Participant(step));
		active = 0;
	}
	
	private void add(Participant participant) {
		this.saga.add(participant);
	}
	
	@Override
	public Saga step(Step step) {
		Participant participant = new Participant(step);
		add(participant);
		return this;
	}
	
	public List<Participant> getParticipants() {
		return this.saga;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public Step next() {
		active++;
		Step step = null;
		if (active < saga.size()) {
			step = saga.get(active).getStep();
		}
		return step;
	}
	
	public Step previous() {
		active--;
		Step step = null;
		if (active >= 0 && active < saga.size()) {
			step = saga.get(active).getStep();
		}
		return step;
	}
	
	public Step current() {
		Step step = null;
		Participant participant = saga.get(active);
		if (participant != null) {
			step = participant.getStep();
		}
		return step;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setStep(String step) {
		Step current = current();
		while (current != null && !current.getStep().equalsIgnoreCase(step)) {
			current = next();
		}
	}
	
	public boolean emCompensacao() {
		return emCompensacao;
	}
	
	public void iniciaCompensacao() {
		this.emCompensacao = Boolean.TRUE;
	}
}
 