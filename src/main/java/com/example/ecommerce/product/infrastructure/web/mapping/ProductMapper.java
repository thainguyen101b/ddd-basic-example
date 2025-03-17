package com.example.ecommerce.product.infrastructure.web.mapping;

import com.example.ecommerce.product.OrderProductDto;
import com.example.ecommerce.product.application.command.UpdateProductCommand;
import com.example.ecommerce.product.application.query.ProductDto;
import com.example.ecommerce.product.domain.Product;
import com.example.ecommerce.product.infrastructure.web.dto.ProductUpdateDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    OrderProductDto toOrderProductDto(Product product);

    ProductDto toDto(Product product);

    UpdateProductCommand toCommand(ProductUpdateDTO dto);

}