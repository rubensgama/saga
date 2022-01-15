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
public class PaymentDto {
	private Integer id;
	private Integer idOrder;
	private String status;
	private Double value;
}
