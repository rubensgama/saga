package br.com.rmg.payment.rabbitmq;

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

@Configuration
public class PaymentConfiguration {
	@Value("${rabbitmq.saga.queue}")
	String sagaQueueName;

	@Value("${rabbitmq.saga.exchange}")
	String exchange;

	@Value("${rabbitmq.saga.routing-key}")
	String sagaRoutingkey;

	@Value("${rabbitmq.saga.payment.queue}")
	String 	queueName;

	@Value("${rabbitmq.saga.payment.routing-key}")
	String routingkey;
	
	@Bean
	Queue paymentQueue() {
		return new Queue(queueName, true, false, false);
	}

	@Bean
	DirectExchange getExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding bindingPayment(Queue queue) {
		return BindingBuilder.bind(paymentQueue()).to(getExchange()).with(routingkey);
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
    
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
          new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
