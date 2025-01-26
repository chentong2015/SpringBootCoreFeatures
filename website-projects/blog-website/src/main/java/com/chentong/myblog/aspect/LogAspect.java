package com.chentong.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 处理日志信息的配置，处理界面访问的一些信息，获取并显示
 */
@Aspect
@Component
public class LogAspect {
    //日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //数据流的切面，拦截的类 : 所有的web下的所有类+所有的方法
    @Pointcut("execution(* com.chentong.myblog.web.*.*(..))")
    public void log(){
    }

    /**
     * 在请求界面，调用方法之前要执行的
     */
    @Before("log()")
    public void doBeforePointCut(JoinPoint joinPoint){
        //HttpServletRequest 获取界面request的访问信息;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        //JoinPoint 获取拦截的类和所有的方法 + 方法的名称
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // 记录所有的请求的信息： 使用成员内部类，实例化对象
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("requestLog : {}", requestLog);
    }

    /**
     * 在请求界面之后，调用方法之后要执行的
     */
    @After("log()")
    public void doAfterPointCut(){
        logger.info("------- Do After PointCut --------");
    }

    /**
     最后返回执行
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result : {}", result); // Get the value return
    }

    /**
     * 定义内部的日志类，定义内部类 ： 成员内部类
     */
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                "url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
        }
    }
}
