package com.spring.tester2.controller;

import com.spring.tester2.exception.ProductExistException;
import com.spring.tester2.exception.TestHandlerException;
import com.spring.tester2.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExceptionHandlerController {

    // @ExceptionHandler(ProductExistException.class)
    @PostMapping("/products/exception/{id}")
    public ResponseEntity<String> testPostProduct(@PathVariable("id") String id, @RequestBody Product product) {
        // return new ResponseEntity<>("bad request testing", HttpStatus.BAD_REQUEST);
        throw new ProductExistException("Exception: Product already exists");
    }

    // TODO. @ExceptionHandler 异常处理器中定义的是要处理的异常类型(被标记的方法抛出的异常类型)
    @ExceptionHandler(TestHandlerException.class)
    @PostMapping("/products/handler/{id}")
    public ResponseEntity<Void> testPostProductWithHandler(@PathVariable("id") String id, @RequestBody Product product) {
        throw new TestHandlerException("Exception: Product already exists");
    }
}
