package com.project.manager.notice.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 각 파일의 용량 체크
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileListSizeValidator.class)
public @interface FileListSize {

    String message() default "등록할 수 없는 파일 용량입니다.";

    String[] allows() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 허용된 파일 용량 (MB)
     *
     * @return 용량
     */
    long maxSize() default 1024;

    /**
     * 허용된 최소 용량
     *
     * @return 최소 용량
     */
    long minSize() default 0;
}
