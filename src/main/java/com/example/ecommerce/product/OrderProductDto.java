package com.example.ecommerce.product;

import javax.money.MonetaryAmount;
import java.io.Serializable;

/**
 * DTO for {@link com.example.ecommerce.product.domain.Product}
 */
public record OrderProductDto(Long id, MonetaryAmount price, int quantity) implements Serializable {
}