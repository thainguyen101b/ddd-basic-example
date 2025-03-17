package com.example.ecommerce.order.application.query;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.ecommerce.order.domain.Order}
 */
public record OrderDto(UUID id, String status, List<OrderItemDto> items,
                       LocalDateTime createdDate) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.order.domain.OrderItem}
     */
    public record OrderItemDto(Long id, Long productId, int quantity,
                               MonetaryAmount unitPrice) implements Serializable {
    }
}