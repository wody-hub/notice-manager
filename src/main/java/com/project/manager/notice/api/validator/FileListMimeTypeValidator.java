package com.project.manager.notice.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 업로드 파일 목록의 mime type 검사
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
public class FileListMimeTypeValidator implements ConstraintValidator<FileListMimeType, List<MultipartFile>> {

    private String[] mimeTypes;
    private String primaryType;

    @Override
    public void initialize(FileListMimeType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.mimeTypes = constraintAnnotation.mimeTypes();
        this.primaryType = constraintAnnotation.primaryType();
    }

    @Override
    public boolean isValid(List<MultipartFile> fileList, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(fileList)) {
            return false;
        }

        for (MultipartFile file : fileList) {
            final var result = this.isAllow(file, this.mimeTypes, this.primaryType);
            if (!result) {
                return false;
            }
        }

        return true;
    }

    /**
     * 파일 허용 검사
     *
     * @param file        파일
     * @param mimeTypes   허용된 mime type 목록
     * @param primaryType 허용된 primary type
     * @return 결과
     */
    private boolean isAllow(MultipartFile file, String[] mimeTypes, String primaryType) {
        final var mimeType = file.getContentType();

        final var allowAllMimeTypes = mimeTypes == null || mimeTypes.length == 0;
        final var containMimeTypes = this.contains(mimeTypes, mimeType);
        final var allowAllPrimaryType = StringUtils.hasText(primaryType);
        final var containPrimaryType = Objects.requireNonNull(mimeType).indexOf(primaryType) == 0;

        if (allowAllMimeTypes && allowAllPrimaryType) {
            return true;
        } else if (allowAllMimeTypes) {
            return containPrimaryType;
        } else if (allowAllPrimaryType) {
            return containMimeTypes;
        } else {
            return containPrimaryType || containMimeTypes;
        }
    }

    private boolean contains(String[] mimeTypes, String mimeType) {
        if (mimeTypes != null) {
            for (String type : mimeTypes) {
                if (Objects.equals(type, mimeType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
