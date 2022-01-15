package br.gov.mj.saga.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
@AllArgsConstructor
@ToString
public class SagaDto {
	private String name;
	private String idSaga;
	private OrderDto order;
}
