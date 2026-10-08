package com.example.DeliveryApplication.global.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
         response.setContentType("application/json;charset=UTF-8");
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);

         Map<String, Object> json = new HashMap<>();
         json.put("message", "접근 권한이 없습니다.");
         response.getWriter().print(objectMapper.writeValueAsString(json));
    }
}