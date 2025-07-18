package com.spring.tester1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tester1.controller.model.Product;

public class ProductHelper {

    // Build request body bytes
    public static byte[] getRequestBodyContent() throws JsonProcessingException {
        Product product = new Product("2", "test");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        return json.getBytes();
    }
}
