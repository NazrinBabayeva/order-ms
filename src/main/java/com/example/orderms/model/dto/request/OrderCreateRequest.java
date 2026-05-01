package com.example.orderms.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Order create request")
public class OrderCreateRequest {

    private long userId;

    private Long productId;

    private Long customerId;

    private Long quantity;

    private String deliveryAddress;

    private BigDecimal price;

    private String email;

}