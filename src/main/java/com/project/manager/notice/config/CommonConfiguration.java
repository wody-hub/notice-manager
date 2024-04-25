package com.project.manager.notice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Spring 공통 설정 (Bean 등록)
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CommonConfiguration {

    /**
     * 기본 지역 설정 (Local.KOREA)
     *
     * @return 한국지역 설정 resolver
     */
    @Bean
    public LocaleResolver localResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);

        return sessionLocaleResolver;
    }

    /**
     * model mapper
     *
     * @return model mapper
     */
    @Bean
    public ModelMapper getModelMapper() {
        final ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setFieldMatchingEnabled(true);

        return mapper;
    }

}
