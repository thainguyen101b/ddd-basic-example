package com.example.ecommerce.order.infrastructure.web.dto;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.order.domain.Order}
 */
public record PlaceOrderDto(List<OrderItemDto> items, AddressDto address) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.order.domain.OrderItem}
     */
    public record OrderItemDto(Long productId, int quantity) implements Serializable {
    }

    /**
     * DTO for {@link com.example.ecommerce.order.domain.Address}
     */
    public record AddressDto(
            String street,
            String city,
            String state,
            String zipCode
    ) implements Serializable {
    }
}