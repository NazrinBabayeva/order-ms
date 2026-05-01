package com.example.orderms.service;

import com.example.orderms.model.dto.request.OrderCreateRequest;
import com.example.orderms.model.dto.response.CreateOrderResponse;
import com.example.orderms.model.dto.response.OrderResponseDto;
import com.example.orderms.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    CreateOrderResponse createOrder(Long userId, OrderCreateRequest request);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getAllOrders();

    void updateStatus(Long orderId, OrderStatus status);

    OrderResponseDto cancelOrder(Long orderId, Long userId);
}