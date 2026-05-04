package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.constants.RabbitMqConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiRabbitMqService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final AmqpAdmin amqpAdmin;
    private final ConnectionFactory connectionFactory;
    private final TopicExchange orderExchange;

    private final Map<String, BlockingQueue<Message>> messageBuffers = new ConcurrentHashMap<>();
    private final Map<String, SimpleMessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();

    public String createTemporaryQueue() {
        String queueName = ORDER_EVENTS_QUEUE + "-" + UUID.randomUUID();
        if (messageBuffers.containsKey(queueName)) {
            throw new IllegalStateException("Queue already exists: " + queueName);
        }
        Queue queue = new Queue(queueName, QUEUE_DURABLE, QUEUE_EXCLUSIVE, QUEUE_AUTO_DELETE);
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(orderExchange).with("#");
        amqpAdmin.declareBinding(binding);

        BlockingQueue<Message> messageBuffer = new LinkedBlockingQueue<>(COLLECTOR_CAPACITY);
        messageBuffers.put(queueName, messageBuffer);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(receivedMessage -> {
            BlockingQueue<Message> buffer = messageBuffers.get(queueName);
            if (buffer != null) {
                boolean offered = buffer.offer(receivedMessage);
                if (!offered) {
                    log.warn("Message buffer full for {} - message dropped", queueName);
                }
            }
        });
        container.start();
        listenerContainers.put(queueName, container);

        log.info("Created temporary test queue '{}'", queueName);
        return queueName;
    }

    public void deleteTemporaryQueue(String queueName) {
        SimpleMessageListenerContainer container = listenerContainers.remove(queueName);
        if (container != null) {
            container.stop();
            log.info("Stopped listener for queue '{}'", queueName);
        }
        amqpAdmin.deleteQueue(queueName);
        messageBuffers.remove(queueName);
        log.info("Deleted temporary queue '{}'", queueName);
    }

    public Optional<Message> findMessageByCorrelationId(String queueName, String correlationId, Duration timeout) {
        BlockingQueue<Message> messageBuffer = messageBuffers.get(queueName);
        if (messageBuffer == null) {
            throw new IllegalArgumentException("Message buffer not found for queue: " + queueName);
        }
        AtomicReference<Message> foundMessage = new AtomicReference<>();
        try {
            Awaitility.await()
                    .atMost(timeout)
                    .pollInterval(Duration.ofMillis(200))
                    .until(() -> {
                        Message candidateMessage;
                        while ((candidateMessage = messageBuffer.poll()) != null) {
                            Object correlationHeader = candidateMessage.getMessageProperties().getHeaders().get("correlationId");
                            if (correlationHeader == null) {
                                correlationHeader = candidateMessage.getMessageProperties().getHeaders().get("correlation_id");
                            }
                            if (correlationId.equals(String.valueOf(correlationHeader))) {
                                foundMessage.set(candidateMessage);
                                return true;
                            } else {
                                log.debug("Received unrelated message with correlation header: {}", correlationHeader);
                            }
                        }
                        return false;
                    });
        } catch (Exception e) {
            log.debug("Awaitility wait finished with exception: {}", e.getMessage());
        }
        return Optional.ofNullable(foundMessage.get());
    }

    public void clearMessageBuffer(String queueName) {
        BlockingQueue<Message> messageBuffer = messageBuffers.get(queueName);
        if (messageBuffer != null) {
            messageBuffer.clear();
        }
    }

    public boolean isQueueEmpty(String queueName) {
        BlockingQueue<Message> messageBuffer = messageBuffers.get(queueName);
        if (messageBuffer == null) {
            Message message = rabbitTemplate.receive(queueName, 200);
            boolean empty = message == null;
            if (!empty) log.warn("Queue '{}' is not empty (fallback)!", queueName);
            return empty;
        }
        boolean empty = messageBuffer.isEmpty();
        if (!empty) {
            log.warn("Message buffer for queue '{}' is not empty! size={}", queueName, messageBuffer.size());
        }
        return empty;
    }

    public void assertMessageBodyFields(Message message, DataTable dataTable) throws Exception {
        String messageBody = new String(message.getBody());
        Map<String, Object> messageBodyMap = objectMapper.readValue(messageBody, new TypeReference<>() {});

        Map<String, String> expectedFields = dataTable.asMap(String.class, String.class);
        for (Map.Entry<String, String> entry : expectedFields.entrySet()) {
            String actualKey = entry.getKey();
            String expectedValue = entry.getValue();
            Object actualValue = messageBodyMap.get(actualKey);

            if (actualValue instanceof Number) {
                Double expectedDouble = Double.valueOf(expectedValue);
                Double actualDouble = ((Number) actualValue).doubleValue();
                Assertions.assertThat(actualDouble)
                        .withFailMessage("Field '%s' expected '%s', but was '%s'", actualKey, expectedDouble, actualDouble)
                        .isEqualTo(expectedDouble);
            } else {
                Assertions.assertThat(String.valueOf(actualValue))
                        .withFailMessage("Field '%s' expected '%s', but was '%s'", actualKey, expectedValue, actualValue)
                        .isEqualTo(expectedValue);
            }
        }
    }
}