package com.example.ecommerce.product.infrastructure.web.dto;

import jakarta.validation.constraints.*;

import javax.money.MonetaryAmount;

public record ProductUpdateDTO(
        @NotBlank(message = "product name must not be blank")
        @Size(max = 255, message = "product name length must be less than 255 characters")
        String name,
        @NotNull(message = "product price must not be null")
        MonetaryAmount price,
        @Positive(message = "product quantity must not be negative")
        int quantity
) {
}
