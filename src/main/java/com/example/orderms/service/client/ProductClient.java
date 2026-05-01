package com.example.orderms.service.client;

import com.example.orderms.model.dto.response.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "product-ms", url = "http://localhost:8082/product-ms/api")
public interface ProductClient {

    @GetMapping("/v1/products/{id}")
    ProductResponseDto getProductById(@PathVariable("id") Long id);

    @GetMapping("/v1/products")
    List<ProductResponseDto> getAllProducts();


}

