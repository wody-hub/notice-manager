package com.project.manager.notice.api.vo;

import lombok.*;
import org.springframework.core.io.Resource;

/**
 * 파일 다운로드를 위한 응답 정보
 *
 * @author 정재요
 * @date 2024. 04. 26
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadResVo {

    private String fileName;
    private Resource resource;
}
