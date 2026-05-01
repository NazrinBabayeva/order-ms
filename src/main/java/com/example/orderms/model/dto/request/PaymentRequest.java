package com.example.orderms.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {

    private Long orderId;

    private UUID userId;

    private BigDecimal amount;

    private String currency;
}