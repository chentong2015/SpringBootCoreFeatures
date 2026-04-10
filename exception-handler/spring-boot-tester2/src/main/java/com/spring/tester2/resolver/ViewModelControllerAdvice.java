package com.spring.tester2.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

// 默认通过DefaultHandlerExceptionResolver解析器返回对应的ModelAndView
@ControllerAdvice
public class ViewModelControllerAdvice {

    // TODO. 拦截处理特定异常的抛出，返回特定的Web View页面给用户
    @ExceptionHandler(IOException.class)
    public ModelAndView handleMainException(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        // 设置要返回的页面Path名称
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
