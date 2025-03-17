package com.example.ecommerce.order.application;

import com.example.ecommerce.order.application.exception.OrderNotFoundException;
import com.example.ecommerce.order.application.query.OrderDto;
import com.example.ecommerce.order.domain.Order;
import com.example.ecommerce.order.domain.OrderId;
import com.example.ecommerce.order.infrastructure.jpa.OrderRepository;
import com.example.ecommerce.order.infrastructure.web.mapping.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDto getOrderById(UUID id) {
        Order order = orderRepository.findById(new OrderId(id))
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toDto(order);
    }

}
