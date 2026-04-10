package com.spring.tester2.resolver;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ResolverControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public void resolveAuthException(AuthenticationException exception) {
        System.out.println("Resolved: " + exception.getMessage());
        // 处理并返回特定的Response Object
    }
}
