package com.mhy.cescsap.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(true){
            return true;
        }
        StringBuffer url = request.getRequestURL();
        log.debug("url is {}", url);
//        if(url.toString().contains("login")){
//            return true;
//        }

        String method = request.getMethod();
        if ("options".equalsIgnoreCase(method)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if(token == null){
            throw new BusinessException(ExceptionType.BUS_INVALID_TOKEN, "Invalid authorization");
        }
        if(!JwtUtils.verifyToken(token) || token.isEmpty()){
            throw new BusinessException(ExceptionType.BUS_INVALID_TOKEN, "Invalid authorization");
        } else {
            log.info("token");
            Claim unClaim = JwtUtils.getClaimByName(token, "userName");
            String userName = unClaim.asString();
            log.debug("userName is {}", userName);
            String strId = JwtUtils.getAudience(token);
            log.debug("accountId is {}", strId);
            request.setAttribute("accountId",Integer.parseInt(strId));
            log.debug("what happened?");
            return true;
        }
    }
}
