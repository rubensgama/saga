package br.com.rmg.saga;

import java.util.concurrent.Executor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class SagaConfiguration {
	/**
	 * Saga rabbit configuration.
	 */
	@Value("${rabbitmq.saga.queue}")
	public String sagaQueueName;

	@Value("${rabbitmq.saga.exchange}")
	public String sagaExchange;

	@Value("${rabbitmq.saga.routing-key}")
	public String sagaRoutingKey;

	/**
	 * Saga order configuration.
	 */
	@Value("${rabbitmq.saga.order.queue}")
	public String orderQueueName;

	@Value("${rabbitmq.saga.order.routing-key}")
	public String orderRoutingKey;

	/**
	 * Saga payment configuration.
	 */
	@Value("${rabbitmq.saga.payment.queue}")
	public String paymentQueueName;
	
	@Value("${rabbitmq.saga.payment.routing-key}")
	public String paymentRoutingKey;
	
	/**
	 * Saga delivery configuration.
	 */
	@Value("${rabbitmq.saga.delivery.queue}")
	public String deliveryQueueName;
	
	@Value("${rabbitmq.saga.delivery.routing-key}")
	public String deliveryRoutingKey;
	
	@Value("${saga.timeout}")
	public int sagaTimeout;
	
	@Bean
	public Queue sagaQueue() {
		return new Queue(sagaQueueName, true, false, false);
	}
	
	@Bean
	public DirectExchange getSagaExchange() {
		return new DirectExchange(this.sagaExchange);
	}

	@Bean
	public Binding bindingSaga() {
		return BindingBuilder.bind(sagaQueue()).to(getSagaExchange()).with(sagaRoutingKey);
	}
	
	@Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
	public String getRoutingKey(Step nextStep) {
		String routingKey = "";
		if (nextStep.equals(Step.ORDER)) {
			routingKey = this.orderRoutingKey;
		} else
		if (nextStep.equals(Step.PAYMENT)) {
			routingKey = this.paymentRoutingKey;
		} else
		if (nextStep.equals(Step.DELIVERY)) {
			routingKey = this.deliveryRoutingKey;
		}
		return routingKey;
	}
	
	@Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
          new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
