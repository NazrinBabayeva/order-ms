package com.example.orderms.service.impl;

import com.example.orderms.enums.OrderStatus;
import com.example.orderms.model.dto.request.OrderCreateRequest;
import com.example.orderms.model.dto.response.CreateOrderResponse;
import com.example.orderms.model.dto.response.OrderResponseDto;
import com.example.orderms.model.entity.Order;
import com.example.orderms.repository.OrderRepository;
import com.example.orderms.event.OrderEventProducer;
import com.example.orderms.service.OrderService;
import com.example.orderms.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderEventProducer orderEventProducer;

    @Override
    public CreateOrderResponse createOrder(Long userId, OrderCreateRequest request) {

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setCustomerId(userId);
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.PAYMENT_PROCESSING);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setActive(true);

        orderRepository.save(order);

        orderEventProducer.sendOrderCreatedEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity().intValue(),
                request.getPrice(),
                request.getEmail(),
                order.getStatus(),
                userId
        );

        return new CreateOrderResponse(order.getId(), order.getStatus());
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        order.setLastActiveChange("Updated by saga orchestrator");

        orderRepository.save(order);
    }

    @Override
    public OrderResponseDto cancelOrder(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setActive(false);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return orderMapper.toDto(order);
    }
}