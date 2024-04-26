package com.project.manager.notice.api.controller;

import com.project.manager.notice.api.exception.DataInvalidException;
import com.project.manager.notice.api.service.NoticeService;
import com.project.manager.notice.api.validator.NoticeModValidator;
import com.project.manager.notice.api.vo.NoticeModVo;
import com.project.manager.notice.api.vo.NoticeReqVo;
import com.project.manager.notice.api.vo.NoticeResVo;
import com.project.manager.notice.config.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 공지사항 관리 controller.
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeModValidator noticeModValidator;

    /**
     * 공지사항 등록
     *
     * @param notice        공지사항 등록 정보
     * @param bindingResult 오류 정보
     * @return 성공 여부
     */
    @PostMapping("/manager/notice")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> saveNotice(@RequestBody @Valid NoticeReqVo notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DataInvalidException(bindingResult);
        }
        this.noticeService.saveNotice(notice);
        return Response.success();
    }

    /**
     * 공지사항 수정
     *
     * @param notice        공지사항 등록 정보
     * @param bindingResult 오류 정보
     * @return 성공 여부
     */
    @PutMapping("/manager/notice")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> updateNotice(@RequestBody @Valid NoticeModVo notice, BindingResult bindingResult) {
        this.noticeModValidator.validate(notice, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new DataInvalidException(bindingResult);
        }
        this.noticeService.updateNotice(notice);
        return Response.success();
    }

    /**
     * 공지사항 상세 정보 조회
     *
     * @param id 공지사항 ID
     * @return 공지사항 상세 정보
     */
    @GetMapping("/manager/notice/{id}")
    public Response<NoticeResVo> getNotice(@PathVariable Long id) {
        return Response.success(this.noticeService.findNoticeDetail(id));
    }


    /**
     * 공지사항 상세 정보 조회
     *
     * @param id 공지사항 ID
     * @return 공지사항 상세 정보
     */
    @DeleteMapping("/manager/notice/{id}")
    public Response<Void> deleteNotice(@PathVariable Long id) {
        this.noticeService.deleteNotice(id);
        return Response.success();
    }
}
