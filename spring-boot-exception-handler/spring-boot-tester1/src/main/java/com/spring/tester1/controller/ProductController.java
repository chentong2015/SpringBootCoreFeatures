package com.spring.tester1.controller;

import com.spring.tester1.controller.model.Product;
import com.spring.tester1.RestTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/products/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        String url = "localhost:5679/products/" + id;
        String response = RestTemplateHelper.sendPostRequest(restTemplate, url, product);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/products/test/{id}")
    public ResponseEntity<String> testInsertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        String url = "localhost:5679/products/test/" + id;
        String response = RestTemplateHelper.sendPostRequest(restTemplate, url, product);
        return ResponseEntity.ok().body(response);
    }
}