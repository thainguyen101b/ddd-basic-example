package com.example.ecommerce.order.application;

import com.example.ecommerce.order.application.command.PlaceOrderCommand;
import com.example.ecommerce.order.domain.Address;
import com.example.ecommerce.order.infrastructure.jpa.OrderRepository;
import com.example.ecommerce.order.infrastructure.web.mapping.OrderMapper;
import com.example.ecommerce.product.OrderProductDto;
import com.example.ecommerce.product.OrderProductQuery;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderProductQuery orderProductQuery;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    PlaceOrderUseCase placeOrderUseCase;

    @Test
    void testExecute() {
        // Arrange for one product
        long productId = 1L;
        int quantity = 2;
        PlaceOrderCommand command = new PlaceOrderCommand(
                List.of(new PlaceOrderCommand.OrderItemCommand(productId, quantity)),
                new PlaceOrderCommand.AddressCommand("123 Main St", "City", "State", "12345")
        );
        OrderProductDto orderProductDto = new OrderProductDto(productId, Money.of(1000, "VND"), 1);
        when(orderProductQuery.findByProductIds(Map.of(productId, quantity))).thenReturn(List.of(orderProductDto));
        when(orderMapper.toAddress(Mockito.any())).thenReturn(new Address("123 Main St", "City", "State", "12345"));

        placeOrderUseCase.execute(command);

        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(orderProductQuery, Mockito.times(1)).findByProductIds(Mockito.any());
    }

}
