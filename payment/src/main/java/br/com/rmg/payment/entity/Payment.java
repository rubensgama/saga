package br.com.rmg.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "PAYMENT", schema = "payment")
public class Payment {
	@Id
	@Column(name = "idPayment")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "idOrder")
	private Integer idOrder;
	@Column(name = "status")
	private String status;
	@Column(name = "value")
	private Double value;
}
