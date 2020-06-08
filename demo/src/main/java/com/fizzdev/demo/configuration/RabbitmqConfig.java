package com.fizzdev.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Bean
    public Queue createQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Exchange directExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Binding queueBinding() {
        return new Binding(queue, Binding.DestinationType.QUEUE, exchange, "", null);
    }
}
