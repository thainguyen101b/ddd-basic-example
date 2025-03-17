package com.example.ecommerce.order.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record OrderId(UUID id) {

    public OrderId {
        Assert.notNull(id, "id must not be null");
    }

    public OrderId() {
        this(UUID.randomUUID());
    }

}
