package com.example.ecommerce.product.application;

import com.example.ecommerce.product.application.command.UpdateProductCommand;
import com.example.ecommerce.product.infrastructure.jpa.ProductRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CreateProductUseCase createProductUseCase;

    @Test
    void testExecute() {
        // arrange
        UpdateProductCommand command = new UpdateProductCommand("foo", Money.of(60000, "VND"), 10);
        createProductUseCase.execute(command);
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }

}
