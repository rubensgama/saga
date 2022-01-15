package br.gov.mj.order;

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
@EnableJpaRepositories(basePackages = {"br.gov.mj.order.repository", "br.gov.mj.saga.relay.repository"})
@ComponentScan(basePackages = {"br.gov.mj.saga.service", "br.gov.mj.order.rabbitmq", "br.gov.mj.order.relay", "br.gov.mj.order.service", "br.gov.mj.saga.event", "br.gov.mj.order.event"})
@EntityScan(basePackages = {"br.gov.mj.order.entity"})
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
