package com.example.ecommerce.order.domain;

public record Address(
        String street,
        String city,
        String state,
        String zipCode
) {
}
