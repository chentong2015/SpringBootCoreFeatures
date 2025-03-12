package com.spring.tester2.controller;

import com.spring.tester2.exception.ProductExistException;
import com.spring.tester2.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    private final Map<String, Product> mockProducts;

    public ProductController() {
        this.mockProducts = new HashMap<>();
        mockProducts.put("1", new Product("1", "apple"));
    }

    // Mock模拟在Server端持久层可能抛出的内部错误，触发指定的异常处理方法
    @PostMapping("/products/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        if (mockProducts.containsKey(id)) {
            throw new ProductExistException("Product already exists");
        }
        mockProducts.put(id, product);
        return new ResponseEntity<>("Product insert successfully", HttpStatus.OK);
    }

    @PostMapping("/products/test/{id}")
    public ResponseEntity<String> testInsertProductException(@PathVariable("id") String id, @RequestBody Product product) {
        return new ResponseEntity<>("Test products exception", HttpStatus.BAD_REQUEST);
    }
}
