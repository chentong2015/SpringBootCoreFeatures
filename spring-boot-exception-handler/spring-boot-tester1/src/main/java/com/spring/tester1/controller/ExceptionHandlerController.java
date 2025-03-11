package com.spring.tester1.controller;

import com.spring.tester1.exception.TestHandlerException;
import com.spring.tester1.model.Product;
import com.spring.tester1.service.ProductService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class ExceptionHandlerController {

    private final ProductService productService;

    @Autowired
    public ExceptionHandlerController(ProductService productService) {
        this.productService = productService;
    }

    // TODO. 捕获异常后, 在tester1层的controller没有抛出异常
    //  而是拿到对应错误信息和httpStatus, 返回相同的ResponseEntity<>
    @PostMapping("/products/exception/{id}")
    public ResponseEntity<String> testInsertProductException(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            productService.testInsertProductException(id, product);
            URI uri = UriComponentsBuilder
                    .fromPath("/v1/statics/data/{id}")
                    .buildAndExpand("e17dd1f1")
                    .toUri();
            return ResponseEntity.created(uri).build(); // .body("success")

        } catch (FeignException exception) {
            System.out.println("Exception content: " + exception.contentUTF8());
            HttpStatus httpStatus = HttpStatus.valueOf(exception.status());
            Optional<ByteBuffer> response = exception.responseBody();
            if (response.isPresent()) {
                String error = StandardCharsets.UTF_8.decode(response.get()).toString();
                System.out.println("error ---- " + error);
                return new ResponseEntity<>(error, httpStatus);
            }
            return new ResponseEntity<>("error: without response body", httpStatus);
        }
    }

    @PostMapping("/products/handler/{id}")
    public ResponseEntity<String> insertProduct(@PathVariable("id") String id, @RequestBody Product product) {
        try {
            productService.testInsertProductHandler(id, product);
            System.out.println("test post handler ok");
            return ResponseEntity.ok().build();

        } catch (TestHandlerException handlerException) {
            // 根据捕获到的异常，能够mapping到指定的Response httpCode
            // 无法准确获取后端的异常信息message !!
            // {"timestamp":"2022-05-27T14:09:09.267+00:00","status":400,"error":"Bad Request","path":"/products/handler/3"}
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
        }
    }
}
