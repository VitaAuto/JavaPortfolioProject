package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, TopicExchange topicExchange, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
        this.objectMapper = objectMapper;
    }

    public void publishEvent(String eventType, Object payload, String correlationId) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            MessageProperties props = new MessageProperties();
            props.setHeader("eventType", eventType);
            props.setHeader("correlationId", correlationId);
            Message message = new Message(jsonPayload.getBytes(), props);
            rabbitTemplate.send(topicExchange.getName(), eventType, message);
        } catch (Exception e) {
            System.err.println("RabbitMQ publish failed: " + e.getMessage());
        }
    }

    public String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}