package com.example.main.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionGlobalHandler  {

    // TODO. 抛出MaxUploadSize异常时可能还没有进入Controller控制器, 此处Handler无效
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return ResponseEntity
                .status(HttpStatus.CONTENT_TOO_LARGE) // 413
                .body("File is too large, maximum allowed size is exceeded.");
    }
}
