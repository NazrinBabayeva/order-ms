package com.example.orderms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncementResponse {

    private Long id;

    private Long productId;

    private Boolean active;

    private BigDecimal price;

    private Long createdBy;

    private LocalDateTime createdAt;
}