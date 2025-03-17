package com.example.ecommerce.product.infrastructure.web;

import com.example.ecommerce.product.application.CreateProductUseCase;
import com.example.ecommerce.product.application.ProductQueryService;
import com.example.ecommerce.product.application.query.ProductDto;
import com.example.ecommerce.product.infrastructure.web.dto.ProductUpdateDTO;
import com.example.ecommerce.product.infrastructure.web.mapping.ProductMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/products")
@RequiredArgsConstructor
class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper productMapper;
    private final ProductQueryService productQueryService;

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productQueryService.findProductById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductUpdateDTO dto) {
        Long productId = createProductUseCase.execute(productMapper.toCommand(dto));
        return ResponseEntity.created(URI.create("/products/%s".formatted(productId))).build();
    }

}
