package com.example.orderms.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Order response")
public class OrderResponseDto {

    private Long id;
    private Long productId;
    private Long customerId;
    private Long quantity;
    private Boolean active;
    private String lastActiveChange;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}