package com.spring.tester1.advice;

import com.spring.tester1.advice.exception.InternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.InvocationTargetException;

@ControllerAdvice
public class ProductControllerAdvice {

    // TODO. 处理RestTemplate请求的异常, 等效抛出
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception) {
        System.out.println(exception.getMessage());
        
        HttpStatus httpStatus = (HttpStatus) exception.getStatusCode();
        return new ResponseEntity<>(exception.getMessage(), httpStatus);
    }

    // TODO. 处理由于Mock方法抛出的异常, 同于测试
    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<String> handleInvocationTargetException(InvocationTargetException invocationException) {
        HttpClientErrorException cause = (HttpClientErrorException) invocationException.getCause();
        System.out.println(cause.getMessage());

        HttpStatus httpStatus = (HttpStatus) cause.getStatusCode();
        return new ResponseEntity<>(cause.getMessage(), httpStatus);
    }

    // 处理Controller请求抛出的异常异常
    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerException(InternalServerException exception) {
        System.out.println("Internal Server exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}