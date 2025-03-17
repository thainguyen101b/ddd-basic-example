package com.example.ecommerce.product.domain;

import com.example.ecommerce.core.MonetaryAmountConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount price;
    private int quantity;

    protected Product(){}

    public Product (String name, MonetaryAmount price, int quantity) {
        Assert.hasText(name, "product name must not be blank");
        Assert.notNull(price, "product price must not be null");
        Assert.isTrue(quantity >= 0, "product quantity must not be negative");

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean available() {
        return quantity > 0;
    }

}
