package com.project.manager.notice.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파일의 허용된 mime type 검사
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileListMimeTypeValidator.class)
public @interface FileListMimeType {
    String message() default "허용되지 않는 type입니다.";

    String[] allows() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 허용된 mime type
     *
     * @return mime type list
     */
    String[] mimeTypes() default {};

    /**
     * 허용된 mime type의 primary
     *
     * @return primary 값
     */
    String primaryType() default "";
}
