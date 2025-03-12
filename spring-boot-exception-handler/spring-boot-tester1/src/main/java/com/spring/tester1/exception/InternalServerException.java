package com.spring.tester1.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
