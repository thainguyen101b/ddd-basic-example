package com.example.ecommerce.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {
    @EmbeddedId
    private OrderId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /*items*/
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    private Address address;

    private LocalDateTime createdDate;

    @Version
    private Long version;

    protected Order() {}

    public Order(List<OrderItem> items, Address address, UserId userId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(items, "items must not be null");
        Assert.notNull(address, "address must not be null");

        this.id = new OrderId();
        this.userId = userId;
        this.items.addAll(items);
        this.status = OrderStatus.PENDING;
        this.address = address;
        createdDate = LocalDateTime.now();
    }

    public MonetaryAmount getTotalPrice() {
        return this.items.stream()
                .map(OrderItem::getPrice)
                .reduce(MonetaryAmount::add).orElse(null);
    }

    public void markFinished() {
        if (this.status == OrderStatus.FINISHED) {
            throw new IllegalStateException("Order is already finished");
        }
        this.status = OrderStatus.FINISHED;
    }

}
