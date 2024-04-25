package com.project.manager.notice.api.validator;

import com.project.manager.notice.api.service.NoticeService;
import com.project.manager.notice.api.vo.NoticeModVo;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 공지사항 수정 검증
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@Component
@RequiredArgsConstructor
public class NoticeModValidator implements Validator {

    private final NoticeService noticeService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NoticeModVo.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final var noticeModVo = (NoticeModVo) target;

        final var notice = this.noticeService.findNoticeById(noticeModVo.getId());
        if (notice == null) {
            errors.rejectValue("id", "notice.noticeId.not.exist");
        }
    }
}
