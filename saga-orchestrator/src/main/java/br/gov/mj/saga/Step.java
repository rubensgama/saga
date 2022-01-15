package br.gov.mj.saga;

public enum Step {
	DELIVERY("DELIVERY", null),
	PAYMENT("PAYMENT", DELIVERY),
	ORDER("ORDER", PAYMENT);
	
	private String name;
	private Step next;
	
	private Step(String name, Step step) {
		this.name = name;
		this.next = step;
	}
	public String getStep() {
		return this.name;
	}
	public Step getNext() {
		return this.next;
	}
}
