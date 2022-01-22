package br.com.rmg.order;

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
@EnableJpaRepositories(basePackages = {"br.com.rmg.order.repository", "br.com.rmg.saga.relay.repository"})
@ComponentScan(basePackages = {"br.com.rmg.saga.service", "br.com.rmg.order.rabbitmq", "br.com.rmg.order.relay", "br.com.rmg.order.service", "br.com.rmg.saga.event", "br.com.rmg.order.event"})
@EntityScan(basePackages = {"br.com.rmg.order.entity"})
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
