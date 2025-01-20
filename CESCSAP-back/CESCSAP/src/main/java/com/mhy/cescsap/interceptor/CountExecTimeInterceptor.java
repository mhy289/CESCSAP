package com.mhy.cescsap.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class CountExecTimeInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long execTime = endTime - startTime;
        //HandlerMethod hm = (HandlerMethod) handler;
        //String name = hm.getMethod().getName();
        System.out.println(" 方法 "+request.getMethod()+" : "+request.getRequestURI()+" Execution time: " + execTime + " ms");
    }
}
