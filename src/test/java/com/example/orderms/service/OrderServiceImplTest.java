package com.example.orderms.service;

import com.example.orderms.enums.OrderStatus;
import com.example.orderms.event.OrderEventProducer;
import com.example.orderms.mapper.OrderMapper;
import com.example.orderms.model.dto.request.OrderCreateRequest;
import com.example.orderms.model.dto.response.CreateOrderResponse;
import com.example.orderms.model.dto.response.OrderResponseDto;
import com.example.orderms.model.entity.Order;
import com.example.orderms.repository.OrderRepository;
import com.example.orderms.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderEventProducer orderEventProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private OrderResponseDto responseDto;
    private OrderCreateRequest request;

    @BeforeEach
    void setUp() {

        order = new Order();
        order.setId(1L);
        order.setProductId(10L);
        order.setCustomerId(5L);
        order.setQuantity(2L);
        order.setStatus(OrderStatus.PAYMENT_PROCESSING);
        order.setDeliveryAddress("Baku");
        order.setActive(true);

        responseDto = new OrderResponseDto();
        responseDto.setId(1L);

        request = new OrderCreateRequest();
        request.setProductId(10L);
        request.setQuantity(2L);
        request.setPrice(BigDecimal.valueOf(100.0));
        request.setEmail("test@gmail.com");
        request.setDeliveryAddress("Baku");
    }

    @Test
    void createOrder_ShouldCreateSuccessfully() {

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        CreateOrderResponse response =
                orderService.createOrder(5L, request);

        assertNotNull(response);

        verify(orderRepository, times(1))
                .save(any(Order.class));

        verify(orderEventProducer, times(1))
                .sendOrderCreatedEvent(
                        any(),
                        any(),
                        anyInt(),
                        any(BigDecimal.class),
                        anyString(),
                        any(),
                        any()
                );
    }

    @Test
    void getOrderById_ShouldReturnOrder() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderMapper.toDto(order))
                .thenReturn(responseDto);

        OrderResponseDto result =
                orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotFound() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> orderService.getOrderById(1L));

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void getAllOrders_ShouldReturnAllOrders() {

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        when(orderMapper.toDto(order))
                .thenReturn(responseDto);

        List<OrderResponseDto> result =
                orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    void updateStatus_ShouldUpdateSuccessfully() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(order))
                .thenReturn(order);

        orderService.updateStatus(1L, OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, order.getStatus());

        verify(orderRepository).save(order);
    }

    @Test
    void cancelOrder_ShouldCancelSuccessfully() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(orderRepository.save(order))
                .thenReturn(order);

        when(orderMapper.toDto(order))
                .thenReturn(responseDto);

        OrderResponseDto result =
                orderService.cancelOrder(1L, 5L);

        assertNotNull(result);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertFalse(order.getActive());

        verify(orderRepository).save(order);
    }
}