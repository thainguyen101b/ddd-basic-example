package com.example.ecommerce.product.application;

import com.example.ecommerce.core.UseCase;
import com.example.ecommerce.product.application.command.UpdateProductCommand;
import com.example.ecommerce.product.domain.Product;
import com.example.ecommerce.product.infrastructure.jpa.ProductRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public Long execute(UpdateProductCommand command) {
        Product product = new Product(command.name(), command.price(), command.quantity());
        productRepository.save(product);
        return product.getId();
    }

}
