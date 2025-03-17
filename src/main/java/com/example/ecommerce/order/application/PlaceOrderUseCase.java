package com.example.ecommerce.order.application;

import com.example.ecommerce.core.UseCase;
import com.example.ecommerce.order.application.command.PlaceOrderCommand;
import com.example.ecommerce.order.domain.*;
import com.example.ecommerce.order.infrastructure.jpa.OrderRepository;
import com.example.ecommerce.order.infrastructure.web.mapping.OrderMapper;

import com.example.ecommerce.product.OrderProductDto;
import com.example.ecommerce.product.OrderProductQuery;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class PlaceOrderUseCase {

    private final OrderProductQuery orderProductQuery;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public String execute(PlaceOrderCommand command) {
        Map<Long, Integer> productIdsAndQuantities = command.items().stream().collect(Collectors.toMap(
                PlaceOrderCommand.OrderItemCommand::productId,
                PlaceOrderCommand.OrderItemCommand::quantity));

        List<OrderProductDto> products = orderProductQuery.findByProductIds(productIdsAndQuantities);
        List<OrderItem> orderItems = mapToOrderItems(products, command.items());
        Order order = new Order(orderItems, orderMapper.toAddress(command.address()), new UserId(UUID.randomUUID()));
        orderRepository.save(order);
        return order.getId().id().toString();
    }

    private List<OrderItem> mapToOrderItems(List<OrderProductDto> products,
                                            List<PlaceOrderCommand.OrderItemCommand> items) {
        Map<Long, OrderProductDto> productMap = products.stream()
                .collect(Collectors.toMap(OrderProductDto::id, Function.identity()));
        return items.stream()
                .map(item -> {
                    OrderProductDto orderProductDTO = productMap.get(item.productId());

                    return new OrderItem(
                            orderProductDTO.id(),
                            item.quantity(),
                            orderProductDTO.price()
                    );
                })
                .toList();
    }


}
