package com.example.orderms.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnnouncementResponse {

    private Long id;

    private Long productId;

    private Boolean active;

    private BigDecimal price;

    private Long createdBy;

    private LocalDateTime createdAt;
}