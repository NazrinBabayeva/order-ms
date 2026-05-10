package com.example.orderms.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {

    private Long orderId;

    private UUID userId;

    private BigDecimal amount;

    private String currency;
}