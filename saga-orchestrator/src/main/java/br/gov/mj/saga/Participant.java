package br.gov.mj.saga;

public class Participant {
	private Step step;
	private String key;
	
	public Participant(Step step) {
		this.step = step;
	}
	
	public Step getStep() {
		return this.step;
	}
	
	public String getKey() {
		return key;
	}
}
