package com.chentong.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 拦截有@Controller标识: 拦截所有异常的信息，包括404和500异常，处理并显示error
@ControllerAdvice
public class ControllerExceptionHandler {

    // 日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 拦截所有的页面的异常Exception 异常的级别类class
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        // , 占位作用: 记录，控制台输出
        logger.error("Request URL : {}, Exception : {} ", request.getRequestURL(),e);

        // 如果异常是自定义指定了状态码的(HttpStatus.NOT_FOUND)，那么则不通过拦截到error的界面
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // TODO. 自定义在异常发生时，返回给用户的Web View
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        // 设置要返回的页面Path名称
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
