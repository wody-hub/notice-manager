package com.project.manager.notice.config.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 성공 응답.
 *
 * @param <T>
 * @author 정재요
 * @date 2024. 04. 25
 */
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2076109991168364976L;

    /** 성공 메시지 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    /** 성공 여부 */
    private ResponseResultCode code;

    /** 응답 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 성공 response 생성
     *
     * @param <T> 응답 type
     * @return 성공 response
     */
    public static <T> Response<T> success() {
        return Response.<T>builder()
                .message("성공")
                .code(ResponseResultCode.SUCCESS)
                .build();
    }

    /**
     * 성공 response 생성
     *
     * @param message 응답 메시지
     * @param <T>     응답 type
     * @return 성공 response
     */
    public static <T> Response<T> success(String message) {
        return Response.<T>builder()
                .message(message)
                .code(ResponseResultCode.SUCCESS)
                .build();
    }

    /**
     * 성공 response 생성
     *
     * @param data 응답 body
     * @param <T>  응답 type
     * @return 성공 response
     */
    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .message("성공")
                .code(ResponseResultCode.SUCCESS)
                .data(data)
                .build();
    }

    /**
     * 성공 response 생성
     *
     * @param message 응답 메시지
     * @param data    응답 body
     * @param <T>     응답 type
     * @return 성공 response
     */
    public static <T> Response<T> success(String message, T data) {
        return Response.<T>builder()
                .message(message)
                .code(ResponseResultCode.SUCCESS)
                .data(data)
                .build();
    }

    /**
     * 실패 response 생성
     *
     * @param <T> 응답 type
     * @return 실패 response
     */
    public static <T> Response<T> error() {
        return Response.<T>builder()
                .message("시스템 오류가 발생하였습니다.")
                .code(ResponseResultCode.FAIL)
                .build();
    }

    /**
     * 실패 response 생성
     *
     * @param <T> 응답 type
     * @return 실패 response
     */
    public static <T> Response<T> error(String message) {
        return Response.<T>builder()
                .message(message)
                .code(ResponseResultCode.FAIL)
                .build();
    }

    /**
     * 실패 response 생성
     *
     * @param data 실패 응답 Data
     * @param <T>  응답 type
     * @return 실패 response
     */
    public static <T> Response<T> error(T data) {
        return Response.<T>builder()
                .message("시스템 오류가 발생하였습니다.")
                .code(ResponseResultCode.FAIL)
                .data(data)
                .build();
    }

    /**
     * 실패 response 생성
     *
     * @param message 오류 메시지
     * @param data    실패 응답 Data
     * @param <T>     응답 type
     * @return 실패 response
     */
    public static <T> Response<T> error(String message, T data) {
        return Response.<T>builder()
                .message(message)
                .code(ResponseResultCode.FAIL)
                .data(data)
                .build();
    }
}
