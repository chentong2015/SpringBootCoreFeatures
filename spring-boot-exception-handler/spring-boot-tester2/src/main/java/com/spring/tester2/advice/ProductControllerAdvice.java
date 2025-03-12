package com.spring.tester2.advice;

import com.spring.tester2.exception.ProductExistException;
import com.spring.tester2.exception.TestHandlerException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {

    // TODO. 处理特定异常并返回ResponseEntity和HttpStatus状态码
    @ExceptionHandler(value = ProductExistException.class)
    public ResponseEntity<String> handleProductExistException(ProductExistException exception) {
        System.out.println("Tester2: product exist exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // TODO. 如果异常定义的ResponseStatus状态码，则同等将其抛出给请求端
    @ExceptionHandler(value = TestHandlerException.class)
    public ResponseEntity<String> handleTestHandlerException(TestHandlerException exception) {
        System.out.println("Tester2: test handler exception !!");
        if(AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            // 直接抛出的异常信息给请求端
            // 400 on POST request for "http://localhost:5679/products/handler/2":
            // "{"timestamp":"2025-03-12T16:57:03.943+00:00","status":400,"error":"Bad Request","path":"/products/handler/2"}"
            throw exception;
        }
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
