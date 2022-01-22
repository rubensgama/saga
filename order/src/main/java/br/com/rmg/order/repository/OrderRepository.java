package br.com.rmg.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.rmg.order.entity.Order;
import br.com.rmg.order.entity.OrderDetail;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("select detail from OrderDetail detail where detail.order.id = ?1")
	public List<OrderDetail> findDetailsByOrder(Integer idOrder);
}
