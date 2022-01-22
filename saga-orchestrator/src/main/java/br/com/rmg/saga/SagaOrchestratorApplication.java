package br.com.rmg.saga;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableRabbit
@EnableJpaRepositories("br.com.rmg.saga.relay.repository")
@ComponentScan(basePackages = {"br.com.rmg.saga.event", "br.com.rmg.saga.rabbitmq", "br.com.rmg.saga"})
@SpringBootApplication
public class SagaOrchestratorApplication {
	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestratorApplication.class, args);
	}
}
