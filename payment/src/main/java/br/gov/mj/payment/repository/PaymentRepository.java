package br.gov.mj.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mj.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
