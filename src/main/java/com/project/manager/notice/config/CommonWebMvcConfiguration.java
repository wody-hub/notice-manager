package com.project.manager.notice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 설정
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommonWebMvcConfiguration implements WebMvcConfigurer {

    /** 허용할 도메인 */
    @Value("${service.cors.origins}")
    private String[] allowedOrigins;

    /** 허용할 method */
    @Value("${service.cors.methods}")
    private String[] allowedMethods;

    /** 추가로 허용할 header */
    @Value("${service.cors.headers}")
    private String[] exposedHeaders;

    /**
     * cors 허용 적용.
     *
     * @param registry cors 등록 객체
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(this.allowedOrigins)
                .allowedMethods(this.allowedMethods)
                .allowedHeaders(this.exposedHeaders)
                .allowCredentials(true);
    }

}




















    


