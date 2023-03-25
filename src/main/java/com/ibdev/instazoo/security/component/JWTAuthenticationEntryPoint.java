package com.ibdev.instazoo.security.component;

import com.google.gson.Gson;
import com.ibdev.instazoo.payload.response.InvalidLoginResponse;
import com.ibdev.instazoo.security.constant.SecurityConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for catching authorization errors
 */
@Log4j2
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        try {
            InvalidLoginResponse loginResponse = new InvalidLoginResponse();
            String jsonLoginResponse = new Gson().toJson(loginResponse);
            response.setContentType(SecurityConstant.CONTENT_TYPE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println(jsonLoginResponse);
        } catch (Exception e) {
            log.error("Error to authenticate: {}", e.getMessage());
        }
    }
}
