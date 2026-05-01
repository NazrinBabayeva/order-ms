package com.example.orderms.mapper;

import com.example.orderms.event.OrderCreatedEvent;
import com.example.orderms.model.dto.request.OrderCreateRequest;
import com.example.orderms.model.dto.request.PaymentRequest;
import com.example.orderms.model.dto.response.OrderResponseDto;
import com.example.orderms.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderCreateRequest request);

    OrderResponseDto toDto(Order order);

    PaymentRequest toPaymentRequest(Order order);

    OrderCreatedEvent toOrderCreatedEvent(Order order);
}