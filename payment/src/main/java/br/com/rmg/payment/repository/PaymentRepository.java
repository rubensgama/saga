package br.com.rmg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rmg.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
