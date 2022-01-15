package br.gov.mj.saga;

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
@EnableJpaRepositories("br.gov.mj.saga.relay.repository")
@ComponentScan(basePackages = {"br.gov.mj.saga.event", "br.gov.mj.saga.rabbitmq", "br.gov.mj.saga"})
@SpringBootApplication
public class SagaOrchestratorApplication {
	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestratorApplication.class, args);
	}
}
