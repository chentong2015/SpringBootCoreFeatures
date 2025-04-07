package com.spring.tester2.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.naming.AuthenticationException;

@Component
public class ExceptionResolverComponent {

    private final HandlerExceptionResolver exceptionResolver;

    // TODO. HandlerExceptionResolver默认的注入两个不同名称的Bean: errorAttributes, handlerExceptionResolver
    // 必须通过名称指定要依赖注入的Bean对象
    public ExceptionResolverComponent(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    // TODO. 使Component抛出的特定异常能被自定义的ControllerAdvice中方法处理
    public void testResolveException(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        this.exceptionResolver.resolveException(request, response, null, exception);
    }

    // 该方法抛出的异常不会被ControllerAdvice中方法处理
    public void testThrowException() throws Exception {
        System.out.println("Throw exception inside component");
        throw new AuthenticationException("Auth Exception");
    }
}
