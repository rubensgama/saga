package br.gov.mj.payment;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableRabbit
@EnableJpaRepositories(basePackages = {"br.gov.mj.payment.repository", "br.gov.mj.saga.relay.repository"})
@ComponentScan(basePackages = {"br.gov.mj.saga.service", "br.gov.mj.payment.rabbitmq", "br.gov.mj.payment.relay", "br.gov.mj.payment.service", "br.gov.mj.saga.event", "br.gov.mj.payment.event"})
@EntityScan(basePackages = {"br.gov.mj.payment.entity"})
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

}
