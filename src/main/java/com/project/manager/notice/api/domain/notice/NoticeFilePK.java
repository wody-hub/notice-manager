package com.project.manager.notice.api.domain.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 공지사항 파일 KEY
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Getter
@Setter
@ToString
public class NoticeFilePK {

    private long noticeId;

    private long fileId;
}
