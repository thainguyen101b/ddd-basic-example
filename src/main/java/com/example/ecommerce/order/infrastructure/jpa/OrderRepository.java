package com.example.ecommerce.order.infrastructure.jpa;

import com.example.ecommerce.order.domain.Order;
import com.example.ecommerce.order.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
