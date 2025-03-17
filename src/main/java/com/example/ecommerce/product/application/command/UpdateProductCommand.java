package com.example.ecommerce.product.application.command;

import javax.money.MonetaryAmount;

public record UpdateProductCommand(
        String name,
        MonetaryAmount price,
        int quantity
) {
}
