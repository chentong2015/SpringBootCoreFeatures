package com.spring.tester1.controller;

import com.spring.tester1.controller.model.Product;
import com.spring.tester1.RestTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExceptionHandlerController {

    private final RestTemplate restTemplate;

    @Autowired
    public ExceptionHandlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO. HTTP请求返回的异常状态将被ControllerAdvice处理，并返回同样状态给调用端
    @PostMapping("/products/exception/{id}")
    public ResponseEntity<String> testInsertProductException(@PathVariable("id") String id, @RequestBody Product product) {
        String url = "http://localhost:5679/products/exception/" + id;
        String response = RestTemplateHelper.sendPostRequest(restTemplate, url, product);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/products/handler/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        String url = "http://localhost:5679/products/handler/" + id;
        String response = RestTemplateHelper.sendPostRequest(restTemplate, url, product);
        return ResponseEntity.ok().body(response);
    }
}