package com.example.orderms.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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