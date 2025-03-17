package com.example.ecommerce.order.infrastructure.web;

import com.example.ecommerce.order.application.OrderQueryService;
import com.example.ecommerce.order.application.query.OrderDto;
import com.example.ecommerce.order.application.PlaceOrderUseCase;
import com.example.ecommerce.order.infrastructure.web.dto.PlaceOrderDto;
import com.example.ecommerce.order.infrastructure.web.mapping.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final OrderMapper orderMapper;
    private final OrderQueryService orderQueryService;

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable UUID id) {
        return orderQueryService.getOrderById(id);
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@Valid @RequestBody PlaceOrderDto dto) {
        String orderId = placeOrderUseCase.execute(orderMapper.toOrderCommand(dto));
        return ResponseEntity.created(URI.create("/orders/%s".formatted(orderId))).build();
    }

}
