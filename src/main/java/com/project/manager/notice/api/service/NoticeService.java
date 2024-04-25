package com.project.manager.notice.api.service;

import com.project.manager.notice.api.domain.notice.Notice;
import com.project.manager.notice.api.domain.notice.NoticeFile;
import com.project.manager.notice.api.domain.notice.NoticeRepository;
import com.project.manager.notice.api.vo.NoticeModVo;
import com.project.manager.notice.api.vo.NoticeReqVo;
import com.project.manager.notice.api.vo.NoticeResVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 공지사항 등록
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;
    private final FileService fileService;

    /**
     * 공지사항 정보 조회
     *
     * @param id 공지사항 ID
     * @return 공지사항 정보
     */
    @Transactional(readOnly = true)
    public Notice findNoticeById(Long id) {
        return this.noticeRepository.findById(id).orElse(null);
    }

    /**
     * 공지사항 등록
     *
     * @param noticeReqVo 공지사항
     */
    @Transactional
    public void saveNotice(NoticeReqVo noticeReqVo) {
        final var notice = this.modelMapper.map(noticeReqVo, Notice.class);
        this.noticeRepository.save(notice);

        final var fileIdList = noticeReqVo.getFileIdList();
        if (!CollectionUtils.isEmpty(fileIdList)) {
            notice.setNoticeFiles(fileIdList.stream()
                    .map(id -> NoticeFile.builder()
                            .noticeId(notice.getId())
                            .fileId(id)
                            .build())
                    .toList());
        }
    }

    /**
     * 공지사항 수정
     *
     * @param noticeModVo 공지사항 수정 정보
     */
    @Transactional
    public void updateNotice(NoticeModVo noticeModVo) {
        final var notice = this.findNoticeById(noticeModVo.getId());
        this.modelMapper.map(noticeModVo, notice);

        final var fileIdList = noticeModVo.getFileIdList();
        if (CollectionUtils.isEmpty(fileIdList)) {
            notice.setNoticeFiles(null);
        } else {
            notice.setNoticeFiles(fileIdList.stream()
                    .map(id -> NoticeFile.builder()
                            .noticeId(notice.getId())
                            .fileId(id)
                            .build())
                    .toList());
        }
    }

    /**
     * 공지사항 상세 정보 조회
     *
     * @param id 공지사항 ID
     * @return 공지사항 상세 정보
     */
    @Transactional(readOnly = true)
    public NoticeResVo findNoticeDetail(Long id) {
        final var notice = this.findNoticeById(id);
        final var noticeRes = this.modelMapper.map(notice, NoticeResVo.class);

        final var noticeFiles = notice.getNoticeFiles();
        if (!CollectionUtils.isEmpty(noticeFiles)) {
            noticeRes.setFileList(
                    noticeFiles.stream()
                            .map(noticeFile -> this.fileService.findFile(noticeFile.getFileId()))
                            .toList()
            );
        }

        return noticeRes;
    }
}
