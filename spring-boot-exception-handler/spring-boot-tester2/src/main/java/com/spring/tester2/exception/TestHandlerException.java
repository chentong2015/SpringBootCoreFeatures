package com.spring.tester2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 设置Controller抛出的异常对应的HttpStatus状态码
//
// 一旦异常带有状态码，则ResponseEntity<>中body的的数据如下
// {"timestamp":"2025-03-12T17:11:44.681+00:00","status":400,"error":"Bad Request","path":"/products/handler/2"}
// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TestHandlerException extends RuntimeException {

    public TestHandlerException(String message) {
        super(message);
    }
}