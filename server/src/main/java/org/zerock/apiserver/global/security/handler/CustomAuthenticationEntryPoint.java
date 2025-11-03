package org.zerock.apiserver.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.zerock.apiserver.global.common.response.bad.BadResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BadResponse badResponse;

        String token = request.getHeader("Authorization"); // Bearer 토큰 여부 확인
        if (token == null || token.isEmpty()) {
            // 인증 자체 없음
            badResponse = BadResponse.make4xxResponse("Authentication required: No authentication token provided.");
        } else {
            // 인증 시도는 있었으나 실패
            badResponse = BadResponse.make4xxResponse("Authentication failed: Invalid or expired token.");
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), badResponse);
    }
}
