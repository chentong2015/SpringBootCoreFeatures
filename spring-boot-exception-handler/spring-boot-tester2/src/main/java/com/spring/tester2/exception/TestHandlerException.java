package com.spring.tester2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 设置Controller抛出的异常对应的HttpStatus状态码
// 对应到ResponseEntity<>返回的状态码
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TestHandlerException extends RuntimeException {

    public TestHandlerException(String message) {
        super(message);
    }
}