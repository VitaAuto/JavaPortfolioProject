package org.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order-events");
    }

    @Bean
    public Queue orderEventsQueue() {
        return new Queue("order-events-queue");
    }

    @Bean
    public Binding orderEventsBinding(Queue orderEventsQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderEventsQueue).to(orderExchange).with("#");
    }
}