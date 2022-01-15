package br.gov.mj.saga.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.gov.mj.saga.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
@AllArgsConstructor
@ToString
@MappedSuperclass
public abstract class SagaMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5863556139984775436L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idMessage")
	private Integer id;
	@Column(name="idSaga")
	private String idSaga;
	@Column(name = "name")
	private String name;
	@Column(name = "time")
	private LocalDateTime time;
	@Column(name = "data")
	private String data;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(name = "saga")
	private String saga;
}
