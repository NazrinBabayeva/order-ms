package com.example.orderms.event;

import com.example.orderms.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    private static final String TOPIC = "order-events";

    public void sendOrderCreatedEvent(Long orderId,
                                      Long productId,
                                      int quantity,
                                      BigDecimal price,
                                      String email,
                                      OrderStatus status,
                                      Long userId) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                orderId,
                productId,
                quantity,
                email,
                price,
                status,
                userId
        );

        kafkaTemplate.send(TOPIC, event);
    }
}