package com.example.orderms.controller;

import com.example.orderms.enums.OrderStatus;
import com.example.orderms.model.dto.request.OrderCreateRequest;
import com.example.orderms.model.dto.response.CreateOrderResponse;
import com.example.orderms.model.dto.response.OrderResponseDto;
import com.example.orderms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @Operation(
            summary = "Create new order",
            description = "Creates order by productId and quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public CreateOrderResponse createOrder(@RequestHeader("X-User-Id") Long userId,
                                           @RequestBody OrderCreateRequest request) {
        return orderService.createOrder(userId, request);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get order by ID",
            description = "Returns order details by order ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Returns list of all orders"
    )
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/{orderId}/cancel")
    @Operation(
            summary = "Cancel order (MS layer)",
            description = "Cancels order and triggers saga compensation events"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "409", description = "Order cannot be cancelled"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public OrderResponseDto cancelOrder(

            @Parameter(description = "User ID coming from BFF")
            @RequestHeader("X-User-Id") Long userId,

            @Parameter(description = "Order ID")
            @PathVariable Long orderId
    ) {
        return orderService.cancelOrder(orderId, userId);
    }

    @Operation(
            summary = "Update order status",
            description = "Updates the status of an order by orderId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status value")
    })

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateStatus(

            @Parameter(description = "Order ID", example = "23")
            @PathVariable Long orderId,

            @Parameter(
                    description = "Order status",
                    example = "FAILED"
            )
            @RequestParam OrderStatus status
    ) {
        orderService.updateStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}