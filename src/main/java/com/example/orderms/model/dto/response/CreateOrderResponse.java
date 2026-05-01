package com.example.orderms.model.dto.response;

import com.example.orderms.enums.OrderStatus;

public record CreateOrderResponse (Long id, OrderStatus status) {

}
