package com.crm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle()");

        String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Basic ")){
            String base64Creds = authHeader.substring("Basic ".length());
            byte[] decoderCreds = Base64.getDecoder().decode(base64Creds);
            String creds = new String(decoderCreds, StandardCharsets.UTF_8);

            String[] parts = creds.split(":");

            if(ADMIN_USERNAME.equals(parts[0]) && ADMIN_PASSWORD.equals(parts[1])){
                return true;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
