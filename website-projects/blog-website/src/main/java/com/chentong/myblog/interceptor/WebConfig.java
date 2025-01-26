package com.chentong.myblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Since Spring 5 you just need to implement the interface WebMvcConfigurer
 * 网页的拦截器，设置要拦截的路径，排除掉不作处理的路径
 * @EnableWebMvc should map the static resources by your own configuration.
 *    该标识表示指定的资源访问的路径问题 : https://stackoverflow.com/questions/45536972/how-add-css-and-js-to-spring-boot-application/45537528
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                 .addPathPatterns("/admin/**")
                 .excludePathPatterns("/admin")
                 .excludePathPatterns("/admin/login");
        /** 
         * super 关键字的含义：
         * 子类的成员变量和母类中的一致，那么子类的成员变量将会隐藏母类的成员变量
         * 由于子类继承母类后使用同样的函数方法，对母类的方法进行了重写Override
         * 此时要调用母类的: 构造方法+隐藏的成员变量+被重写的同样的函数方法，则需要使用super关键字符
         */
       // super.addInterceptors(registry);
    }

}
