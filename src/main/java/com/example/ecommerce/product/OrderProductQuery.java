package com.example.ecommerce.product;

import java.util.List;
import java.util.Map;

public interface OrderProductQuery {

    List<OrderProductDto> findByProductIds(Map<Long, Integer> productIdsAndQuantities);

}