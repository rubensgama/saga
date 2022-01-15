package br.gov.mj.order.rabbitmq;

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
public class OrderConfiguration {
	@Value("${rabbitmq.saga.queue}")
	String sagaQueueName;

	@Value("${rabbitmq.saga.exchange}")
	String exchange;

	@Value("${rabbitmq.saga.routing-key}")
	String sagaRoutingkey;

	@Value("${rabbitmq.saga.order.queue}")
	String 	queueName;

	@Value("${rabbitmq.saga.order.routing-key}")
	String routingkey;
	
	@Bean
	Queue orderQueue() {
		return new Queue(queueName, true, false, false);
	}

	@Bean
	DirectExchange getExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding bindingOrder(Queue queue) {
		return BindingBuilder.bind(orderQueue()).to(getExchange()).with(routingkey);
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
