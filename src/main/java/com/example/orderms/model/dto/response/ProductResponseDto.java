package com.example.orderms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private Long count;
    private Boolean active;
    private java.math.BigDecimal price;
}