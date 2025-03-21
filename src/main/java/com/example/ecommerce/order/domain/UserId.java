package com.example.ecommerce.order.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId {
        Assert.notNull(id, "id must not be null");
    }

}
