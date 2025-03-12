package com.spring.tester1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.tester1.model.Product;
import com.spring.tester1.utils.RestTemplateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class ProductController {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    // TODO. 使用Spring Cloud Feign发送请求，报错FeignException异常
    //  通过捕获可以获得和server端一致的报错信息 + StatusCode
    @PostMapping("/products/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            String response = RestTemplateHelper.sendPostRequest(restTemplate, "localhost:", product);
            return ResponseEntity.ok().body(response);
        } catch (FeignException exception) {
            Optional<ByteBuffer> response = exception.responseBody();
            HttpStatus httpStatus = HttpStatus.valueOf(exception.status());
            if (response.isPresent()) {
                String error = StandardCharsets.UTF_8.decode(response.get()).toString();
                System.out.println("error ---- " + error);
                return new ResponseEntity<>(error, httpStatus);
            }
            return new ResponseEntity<>("error", httpStatus);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/products/test/{id}")
    public ResponseEntity<String> testInsertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            String response = RestTemplateHelper.sendPostRequest(restTemplate, "localhost:", product);
            return ResponseEntity.ok().body(response);
        } catch (FeignException | JsonProcessingException exception) {
            // HttpStatus httpStatus = HttpStatus.valueOf(exception.status());
            // Optional<ByteBuffer> response = exception.responseBody();
            // if (response.isPresent()) {
            //     String error = StandardCharsets.UTF_8.decode(response.get()).toString();
            //     System.out.println("error ---- " + error);
            //     return new ResponseEntity<>(error, httpStatus);
            // }
            return new ResponseEntity<>("error: without response body", HttpStatus.BAD_REQUEST);
        }
    }
}
