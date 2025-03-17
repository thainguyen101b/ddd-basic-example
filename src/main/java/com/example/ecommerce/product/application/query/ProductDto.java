package com.example.ecommerce.product.application.query;

import javax.money.MonetaryAmount;

public record ProductDto(
        Long id,
        String name,
        MonetaryAmount price,
        int quantity
) {
}
