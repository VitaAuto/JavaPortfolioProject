package org.example.constants;

import java.time.Duration;

public class RabbitMqConstants {
    public static final String ORDER_EVENTS_QUEUE = "order-events-queue";
    public static final Duration DEFAULT_RABBITMQ_TIMEOUT = Duration.ofSeconds(10);
    public static final boolean QUEUE_DURABLE = false;
    public static final boolean QUEUE_EXCLUSIVE = true;
    public static final boolean QUEUE_AUTO_DELETE = true;
    public static final int COLLECTOR_CAPACITY = 100;
}
