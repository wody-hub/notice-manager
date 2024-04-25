package com.project.manager.notice.api.domain.notice;

import jakarta.persistence.*;
import lombok.*;

/**
 * 공지사항 - 파일 매핑 테이블
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "notice_file", indexes = @Index(name = "notice_id_file_index", columnList = "notice_id"))
@IdClass(NoticeFilePK.class)
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFile {

    @Id
    @Column(name = "notice_id")
    private Long noticeId;

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "notice_id")
    private Notice notice;

}
