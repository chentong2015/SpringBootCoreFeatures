package com.spring.tester2.controller;

import com.spring.tester2.advice.exception.ProductExistException;
import com.spring.tester2.advice.exception.TestHandlerException;
import com.spring.tester2.controller.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExceptionHandlerController {

    @PostMapping("/products/exception/{id}")
    public ResponseEntity<String> testPostProduct(@PathVariable("id") String id, @RequestBody Product product) {
        // return new ResponseEntity<>("bad request testing", HttpStatus.BAD_REQUEST);
        throw new ProductExistException("Product already exists");
    }

    @PostMapping("/products/handler/{id}")
    public ResponseEntity<Void> testPostProductWithHandler(@PathVariable("id") String id, @RequestBody Product product) {
        throw new TestHandlerException("Product already exists");
    }
}
