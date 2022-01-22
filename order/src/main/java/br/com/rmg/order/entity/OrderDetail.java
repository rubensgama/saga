package br.com.rmg.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@Getter
@Setter
@EqualsAndHashCode(exclude = {"order"})
@ToString
@Entity
@Table(name = "ORDER_PROC_DETAIL", schema = "order_process")
public class OrderDetail {
	@Id
	@Column(name = "idOrderDetail")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "item")
	private String item;
	@Column(name = "qtd")
	private Double qtd;
	@Column(name = "value")
	private Double value;
	@JsonIgnore
	@JoinColumn(name = "idOrder")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
}
