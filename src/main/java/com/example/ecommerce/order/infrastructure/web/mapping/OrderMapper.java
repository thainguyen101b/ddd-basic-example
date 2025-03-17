package com.example.ecommerce.order.infrastructure.web.mapping;

import com.example.ecommerce.order.application.query.OrderDto;
import com.example.ecommerce.order.application.command.PlaceOrderCommand;
import com.example.ecommerce.order.domain.Address;
import com.example.ecommerce.order.domain.Order;
import com.example.ecommerce.order.infrastructure.web.dto.PlaceOrderDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "status", source = "status")
    OrderDto toDto(Order order);

    PlaceOrderCommand toOrderCommand(PlaceOrderDto dto);

    Address toAddress(PlaceOrderCommand.AddressCommand addressCommand);

}