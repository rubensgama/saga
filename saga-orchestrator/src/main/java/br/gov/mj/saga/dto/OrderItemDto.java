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
public class OrderItemDto {
	private Integer id;
	private String item;
	private String name;
	private Double qtd;
	private Double value;
}
