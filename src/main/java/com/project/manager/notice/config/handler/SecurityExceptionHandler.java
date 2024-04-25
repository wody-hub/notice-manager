package com.project.manager.notice.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.manager.notice.config.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

/**
 * Security 에서 발생한 오류 처리 Handler.
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityExceptionHandler implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {

    private final ObjectMapper objectMapper;

    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
            if (log.isErrorEnabled()) {
                log.error(accessDeniedException.getMessage(), accessDeniedException);
            }

            final var status = HttpStatus.UNAUTHORIZED;
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            this.objectMapper.writeValue(response.getOutputStream(), Response.error("권한이 없습니다."));
        });
    }
}
