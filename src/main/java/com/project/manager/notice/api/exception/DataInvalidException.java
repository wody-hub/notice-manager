package com.project.manager.notice.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 데이터 검증 오류
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Getter
public class DataInvalidException extends RuntimeException {

    /** validate 수행 시 발생한 오류 결과 */
    private final BindingResult errors;

    /** 응답 형태의 오류 목록 (response 용) */
    private final List<ErrorData> errorList;

    /**
     * 데이터 validate 발생용 Exception Constructor
     *
     * @param bindingResult validate 수행 시 발생한 오류 결과
     */
    public DataInvalidException(BindingResult bindingResult) {
        this.errors = bindingResult;
        this.errorList = this.convertErrorList();
    }

    /**
     * 직접 오류 데이터를 세팅
     *
     * @param errorList 오류 목록
     */
    public DataInvalidException(List<ErrorData> errorList) {
        this.errors = null;
        this.errorList = errorList;
    }

    /**
     * 응답 형태의 오류 목록 (field, message 조합의 list 로 구성됨)
     *
     * @return 오류 목록
     */
    private List<ErrorData> convertErrorList() {
        if (this.errors == null || !this.errors.hasErrors()) {
            return Collections.emptyList();
        }

        final var allErrors = errors.getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            return allErrors.stream()
                    .map(e -> {
                        final var fe = (FieldError) e;
                        return new ErrorData(fe.getField(), fe.getDefaultMessage());
                    })
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 발생한 errorList 의 최상위 item 의 메시지
     *
     * @return 오류 메시지
     */
    @Override
    public String getMessage() {
        final var message = super.getMessage();

        if (StringUtils.hasText(message)) {
            return message;
        }

        if (!CollectionUtils.isEmpty(this.errorList)) {
            final var error = this.errorList.get(0);
            return Optional.ofNullable(error).map(ErrorData::message).orElse("");
        }

        return "";
    }

    /**
     * @author 정재요
     * @date 2024. 04. 24
     */
    public record ErrorData(
            @JsonIgnore // 결과에 필드값은 노출하지 않음.
            String field,
            String message
    ) {
    }
}
