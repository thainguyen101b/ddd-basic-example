package com.example.ecommerce.order.application.command;

import java.util.List;

public record PlaceOrderCommand(
        List<OrderItemCommand> items,
        AddressCommand address
) {

    public record OrderItemCommand(
            Long productId,
            int quantity
    ){}

    public record AddressCommand(
            String street,
            String city,
            String state,
            String zipCode
    ) {
    }
}
