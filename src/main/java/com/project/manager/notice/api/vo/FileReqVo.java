package com.project.manager.notice.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.manager.notice.api.validator.FileListMimeType;
import com.project.manager.notice.api.validator.FileListSize;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 상품 이미지 등록 후 이미지 정보 반환 dto
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileReqVo {

    @NotNull(message = "파일을 선택해주세요.")
    @FileListSize(maxSize = 20)
    @FileListMimeType(mimeTypes = {"image/gif", "image/x-icon", "image/jpeg", "image/jpg", "image/tiff", "image/webp", "image/png", "image/svg+xml", "image/x-ms-bmp", "image/vnd.microsoft.icon", // 이미지
            "video/mp4", "video/mpeg", "video/quicktime", "video/webm", "video/x-flv", "video/x-m4v", "video/x-mng", "video/x-ms-asf", "video/x-ms-wmv", "video/x-msvideo", // 동영상
            "application/pdf", "application/vnd.ms-powerpoint", "application/x-rar-compressed", "application/x-tar",
            "application/zip", "application/vnd.ms-excel", "application/msword", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/x-hwp", "application/vnd.hancom.hwp", "application/haansofthwp", "application/vnd.hancom.hwpx", "application/haansofthwpx"
    }, message = "해당 파일은 업로드 할 수 없습니다.")
    private List<MultipartFile> files;
}
