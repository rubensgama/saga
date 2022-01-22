package br.com.rmg.saga;

import java.util.List;

public interface Saga {
	Saga step(Step step);
	String getName();
	List<Participant> getParticipants();
	Step next();
	Step previous();
	Step current();
	String getId();
	void setId(String id);
	void setStep(String step);
	void iniciaCompensacao();
	boolean emCompensacao();
}
