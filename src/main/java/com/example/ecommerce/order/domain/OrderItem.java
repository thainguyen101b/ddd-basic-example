package com.example.ecommerce.order.domain;

import com.example.ecommerce.core.MonetaryAmountConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;

@Entity
@Getter
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;

    @Convert(converter = MonetaryAmountConverter.class)
    private MonetaryAmount unitPrice;

    protected OrderItem() {}

    public OrderItem(Long productId, int quantity, MonetaryAmount unitPrice) {
        Assert.notNull(productId, "productId must not be null");
        Assert.isTrue(quantity >= 0, "quantity must not be negative");
        Assert.notNull(unitPrice, "unitPrice must not be null");

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public MonetaryAmount getPrice() {
        return this.unitPrice.multiply(this.quantity);
    }

}
