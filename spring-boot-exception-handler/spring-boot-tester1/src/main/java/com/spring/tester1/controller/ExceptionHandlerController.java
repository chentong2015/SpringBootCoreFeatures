package com.spring.tester1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.tester1.exception.TestHandlerException;
import com.spring.tester1.model.Product;
import com.spring.tester1.utils.RestTemplateHelper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class ExceptionHandlerController {

    private final RestTemplate restTemplate;

    @Autowired
    public ExceptionHandlerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO. 捕获异常后, 在tester1层的controller没有抛出异常
    //  而是拿到对应错误信息和httpStatus, 返回相同的ResponseEntity<>
    @PostMapping("/products/exception/{id}")
    public ResponseEntity<String> testInsertProductException(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            String response = RestTemplateHelper.sendPostRequest(restTemplate, "localhost:", product);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException exception) {
            HttpStatus httpStatus = exception.getStatusCode();
            String message = exception.getMessage();
            if (message == null || message.isEmpty()) {
                return new ResponseEntity<>("error: without response body", httpStatus);
            }
            return new ResponseEntity<>(message, httpStatus);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/products/handler/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            String response = RestTemplateHelper.sendPostRequest(restTemplate, "localhost:", product);
            return ResponseEntity.ok().body(response);

        } catch (TestHandlerException handlerException) {
            System.out.println(handlerException.getMessage());
            return new ResponseEntity<>(handlerException.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (FeignException exception) {
            // logger.error("Error: message", exception); 需要提供日志错误的输出
            // System.out.printf(exception.getMessage()); 获取整个的exception的内容信息
            Optional<ByteBuffer> response = exception.responseBody();
            HttpStatus httpStatus = HttpStatus.valueOf(exception.status());
            if (response.isPresent()) {
                String error = StandardCharsets.UTF_8.decode(response.get()).toString();
                return new ResponseEntity<>(error, httpStatus);
            }
            return new ResponseEntity<>("error", httpStatus);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
