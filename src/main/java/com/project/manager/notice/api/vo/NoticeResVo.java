package com.project.manager.notice.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 공지사항 상세 정보
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeResVo {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private List<FileResVo> fileList;
}
