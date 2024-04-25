package com.project.manager.notice.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileListSizeValidator implements ConstraintValidator<FileListSize, List<MultipartFile>> {

    private Long fileMaxSize;
    private Long fileMinSize;

    @Override
    public void initialize(FileListSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.fileMaxSize = constraintAnnotation.maxSize();
        this.fileMinSize = constraintAnnotation.minSize();
    }

    @Override
    public boolean isValid(List<MultipartFile> fileList, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(fileList)) {
            return false;
        }

        for (MultipartFile file : fileList) {
            final var result = this.isAllow(file, this.fileMaxSize, this.fileMinSize);
            if (!result) {
                return false;
            }
        }

        return true;
    }

    /**
     * 해당 파일 사이즈 조건 충족 여부 확인
     *
     * @param file        업로드 파일
     * @param fileMaxSize 최대 사이즈
     * @param fileMinSize 최소 사이즈
     * @return 조건 충족 여부
     */
    public static boolean isAllow(MultipartFile file, Long fileMaxSize, Long fileMinSize) {
        final var fileSize = file.getSize();
        final var fileMegaSize = Math.ceil((double) fileSize / 1024 / 1024);

        return fileMegaSize >= fileMinSize && fileMegaSize <= fileMaxSize;
    }
}
