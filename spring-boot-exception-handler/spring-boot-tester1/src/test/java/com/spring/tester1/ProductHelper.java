package com.spring.tester1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tester1.model.Product;

public class ProductHelper {

    public static byte[] getRequestBodyContent() throws JsonProcessingException {
        Product product = new Product("2", "test");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        return json.getBytes();
    }
}
