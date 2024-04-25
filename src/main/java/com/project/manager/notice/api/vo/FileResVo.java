package com.project.manager.notice.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 파일 등록 후 응답
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1763471699125347169L;

    /** 파일 고유 ID */
    private Long id;

    /** 파일 이름 */
    private String originalFilename;

    /** 파일 이름 */
    private String newFilename;

}
