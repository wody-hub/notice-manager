package com.project.manager.notice.api.exception;

import lombok.NoArgsConstructor;

/**
 * 잘못된 요청 exception
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@NoArgsConstructor
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
