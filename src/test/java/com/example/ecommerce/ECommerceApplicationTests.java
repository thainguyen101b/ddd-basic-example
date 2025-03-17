package com.example.ecommerce;

import com.example.ecommerce.order.infrastructure.web.dto.PlaceOrderDto;
import com.example.ecommerce.product.infrastructure.web.dto.ProductUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ECommerceApplicationTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewProduct() throws Exception {
        // create product success
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new ProductUpdateDTO("foo", Money.of(60000, "VND"), 10))
                )
        ).andExpect(status().isCreated());

        // create product with price invalid
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "foo",
                          "price": "60000.00",
                          "quantity": 10
                        }
                        """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder() throws Exception {
        // arrange product
        String productPostContent = objectMapper.writeValueAsString(
                new ProductUpdateDTO("foo", Money.of(60000, "VND"), 10));
        // act post product
        String productPostLocation = mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(productPostContent))
                .andExpect(status().isCreated()).andReturn().getResponse().getHeader("Location");
        assertNotNull(productPostLocation);
        // act extract id from header
        String productIdStr = productPostLocation.substring(productPostLocation.lastIndexOf('/') + 1);
        Long productId = Long.parseLong(productIdStr);

        // arrange order dto
        String placeOrderContent = objectMapper.writeValueAsString(
                new PlaceOrderDto(
                        List.of(new PlaceOrderDto.OrderItemDto(productId, 1)),
                        new PlaceOrderDto.AddressDto("123 Main St", "City", "State", "12345")));
        // act place order
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                .content(placeOrderContent))
                .andExpect(status().isCreated());

    }

}
