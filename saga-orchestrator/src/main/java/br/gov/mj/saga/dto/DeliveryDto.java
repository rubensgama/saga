package br.gov.mj.saga.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
@AllArgsConstructor
@ToString
public class DeliveryDto {
	private Integer idPayment;
	private Integer idOrder;
	private Double value;
	private LocalDateTime time;
}
