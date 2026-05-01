package com.example.orderms.event;

import com.example.orderms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private Long productId;
    private long quantity;
    private String email;
    private BigDecimal price;
    private OrderStatus status;
    private Long userId;

}
