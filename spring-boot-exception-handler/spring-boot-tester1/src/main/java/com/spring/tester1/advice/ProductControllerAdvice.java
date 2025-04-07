package com.spring.tester1.advice;

import com.spring.tester1.advice.exception.InternalServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ProductControllerAdvice {

    // TODO. 处理RestTemplate请求的异常: 将异常信息等效抛出
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception) {
        String detailsMessage = exception.getMessage();
        System.out.println(detailsMessage);
        
        HttpStatus httpStatus = (HttpStatus) exception.getStatusCode();
        String responseBody = exception.getResponseBodyAsString();
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    // TODO. 处理Controller请求抛出的异常异常
    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerException(InternalServerException exception) {
        System.out.println("Internal Server exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}