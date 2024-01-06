package com.gotcha.vote.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String token = tokenProvider.getAccessToken(request);
        String requestURI = request.getRequestURI();

        try {
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (StringUtils.isNotBlank(token) && tokenProvider.validateAccessToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Security Context에 " + authentication.getName() + "인증 정보를 저장했습니다, uri: " + requestURI);
            } else {
                log.info("유효한 JWT 토큰이 없습니다, uri: " + requestURI);
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            setErrorResponse(response, e.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, String errorMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED.value());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public record ErrorResponse(String message, Integer code){
    }
}