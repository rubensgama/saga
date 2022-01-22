/**
 * 
 */
package br.com.rmg.saga.relay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.rmg.saga.entity.SagaMessage;

/**
 * @author Meta
 */
public interface SagaMessageRepository extends JpaRepository<SagaMessage, Integer> {
	@Query("select mr from SagaMessage mr where mr.id in ( select max(msg.id) from SagaMessage msg group by msg.idSaga ) and (mr.status <> br.com.rmg.saga.enums.Status.FINALIZADO)")
	List<SagaMessage> findLastSagaNotFinished();
}
