package com.example.ecommerce.product.application;

import com.example.ecommerce.product.OrderProductDto;
import com.example.ecommerce.product.OrderProductQuery;
import com.example.ecommerce.product.application.exception.OutOfStockException;
import com.example.ecommerce.product.application.exception.ProductNotFoundException;
import com.example.ecommerce.product.application.query.ProductDto;
import com.example.ecommerce.product.domain.Product;
import com.example.ecommerce.product.infrastructure.jpa.ProductRepository;
import com.example.ecommerce.product.infrastructure.web.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements OrderProductQuery {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<OrderProductDto> findByProductIds(Map<Long, Integer> productIdsAndQuantities) {
        Set<Long> productIds = productIdsAndQuantities.keySet();
        List<Product> productsFound = productRepository.findAllById(productIds);
        if (productsFound.size() != productIds.size()) {
            List<Long> missingProductIds = productsFound.stream().map(Product::getId)
                    .filter(id -> !productIds.contains(id))
                    .toList();
            throw new ProductNotFoundException(missingProductIds);
        }

        checkStockAvailable(productsFound, productIdsAndQuantities);

        return productsFound.stream().map(productMapper::toOrderProductDto).toList();
    }

    private void checkStockAvailable(List<Product> products, Map<Long, Integer> productIdsAndQuantities) {
        List<String> outOfStockProducts = products.stream()
                .filter(product -> product.getQuantity() < productIdsAndQuantities.get(product.getId()))
                .map(product -> "Product ID " + product.getId() + " (Available: " + product.getQuantity() +
                        ", Requested: " + productIdsAndQuantities.get(product.getId()) + ")")
                .toList();
        if (!outOfStockProducts.isEmpty()) {
            throw new OutOfStockException("Products out of stock: " + String.join(", ", outOfStockProducts));
        }
    }

}
