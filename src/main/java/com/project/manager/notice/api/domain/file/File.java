package com.project.manager.notice.api.domain.file;

import jakarta.persistence.*;
import lombok.*;

/**
 * 파일 관리
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "file")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", length = 200, nullable = false)
    private String fileName;

    @Column(name = "file_original_name", length = 200, nullable = false)
    private String fileOriginalName;

    @Column(name = "file_path", length = 200)
    private String filePath;

    @Column(nullable = false)
    private long size;
}
