package com.project.manager.notice.config.handler;

import com.project.manager.notice.api.exception.DataInvalidException;
import com.project.manager.notice.config.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 발생하는 Exception 공통 처리 Advice
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 공통 Exception handler
     *
     * @param exception 공통 exception
     * @return 오류 응답.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleCommonException(Exception exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }

        return ResponseEntity.internalServerError()
                .body(Response.error());
    }

    /**
     * Data validation 오류 발생시 예외처리.
     *
     * @param exception data validate 시 발생하는 DataValidationException
     * @return 오류 응답.
     * @see jakarta.validation.Valid
     * @see org.springframework.validation.annotation.Validated
     */
    @ExceptionHandler(DataInvalidException.class)
    public final ResponseEntity<Object> handleDataInvalidException(DataInvalidException exception) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        return ResponseEntity.badRequest()
                .body(Response.error(exception.getMessage(), exception.getErrorList()));
    }

    /**
     * Data validation 오류 발생시 예외처리.
     *
     * @param exception the exception to handle
     * @param headers   the headers to be written to the response
     * @param status    the selected response status
     * @param request   the current request
     * @return 오류 응답.
     * @see jakarta.validation.Valid
     * @see org.springframework.validation.annotation.Validated
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }

        final var dataException = new DataInvalidException(exception.getBindingResult());
        return ResponseEntity.badRequest().body(Response.error(dataException.getMessage(), dataException.getErrorList()));
    }

    /**
     * 주소를 찾을 수 없음.
     *
     * @param exception the exception to handle
     * @param headers   the headers to use for the response
     * @param status    the status code to use for the response
     * @param request   the current request
     * @return 오류 응답.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull NoHandlerFoundException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("주소를 찾을 수 없습니다."));
    }

    /**
     * 요청 정보를 처리할 수 없을 때 처리
     *
     * @param exception the exception to handle
     * @param headers   the headers to use for the response
     * @param status    the status code to use for the response
     * @param request   the current request
     * @return 오류 응답.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        if (log.isErrorEnabled()) {
            log.error(exception.getMessage(), exception);
        }
        return ResponseEntity.badRequest().body(Response.error("잘못된 요청입니다."));
    }

}
