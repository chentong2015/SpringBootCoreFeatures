package com.spring.tester1.advice;

import com.spring.tester1.exception.InternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ProductControllerAdvice {

    // TODO. 处理RestTemplate请求的异常
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception) {
        HttpStatus httpStatus = (HttpStatus) exception.getStatusCode();
        String message = exception.getMessage();
        if (message == null || message.isEmpty()) {
            System.out.println("Tester1 Error: without response body");
            return new ResponseEntity<>("Empty response", httpStatus);
        }
        return new ResponseEntity<>(message, httpStatus);
    }

    // TODO. 处理Controller请求抛出的异常异常
    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerException(InternalServerException exception) {
        System.out.println("Starter1: internal Server exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}