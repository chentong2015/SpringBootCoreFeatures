package com.spring.tester2.advice;

import com.spring.tester2.exception.InternalServerException;
import com.spring.tester2.exception.ProductExistException;
import com.spring.tester2.exception.TestHandlerException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO. ControllerAdvice类型
// - 全局处理Controller控制器中抛出的异常
// - 根据拦截的异常信息，解析后返回特定的Response
@ControllerAdvice
public class ProductControllerAdvice {

    // TODO. 定义要处理的异常并返回特定的ResponseEntity和HttpStatus状态码
    @ExceptionHandler(value = ProductExistException.class)
    public ResponseEntity<String> handleProductExistException(ProductExistException exception) {
        System.out.println("Handle product exist exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerException(InternalServerException exception) {
        System.out.println("Handle internal Server exception !!");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = TestHandlerException.class)
    public ResponseEntity<String> handleTestHandlerException(TestHandlerException exception) {
        // 如果异常是自定义指定了状态码的(HttpStatus.NOT_FOUND)，那么则不通过拦截到error的界面
        if(AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            throw exception;
        }
        return new ResponseEntity<>("Handler exception", HttpStatus.BAD_REQUEST);
    }
}
