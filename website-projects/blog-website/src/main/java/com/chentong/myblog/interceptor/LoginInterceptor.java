package com.chentong.myblog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网页的过滤器，拦截网页的任意访问admin下的网址
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    // 预处理网页的访问: 判断Session中是否有用户
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
        //return super.preHandle(request, response, handler);
    }
}
