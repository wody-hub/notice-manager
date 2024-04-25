package com.project.manager.notice.api.controller;

import com.project.manager.notice.api.exception.DataInvalidException;
import com.project.manager.notice.api.service.FileService;
import com.project.manager.notice.api.vo.FileReqVo;
import com.project.manager.notice.api.vo.FileResVo;
import com.project.manager.notice.config.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

/**
 * 파일 처리 controller
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 등록
     *
     * @return 등록된 상품 이미지 정보
     */
    @PostMapping("/manager/file")
    public Response<List<FileResVo>> uploadFile(@Valid FileReqVo reqDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new DataInvalidException(errors);
        }
        return Response.success(this.fileService.saveFile(reqDto));
    }

    /**
     * 파일 다운로드 요청
     *
     * @param id 이미지 맵 일련번호
     * @return 다운로드 파일
     */
    @GetMapping("/manager/file/{id}")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable("id") Long id) throws MalformedURLException {
//        final var info = this.fileService.getFileForDownload(imgMapSn);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        httpHeaders.setContentDispositionFormData("attachment", new URLEncoder().encode(info.getImgNm(), StandardCharsets.UTF_8));
//        httpHeaders.set("Content-Transfer-Encoding", "binary");
//
//        return new ResponseEntity<>(new UrlResource(info.getImgLink()), httpHeaders, HttpStatus.OK);
        return null;
    }

    /**
     * 파일 삭제 요청 (이미지, 파일 공통)
     *
     * @param id 이미지 맵 일련번호
     * @return 삭제 성공 여부
     */
    @DeleteMapping("/manager/file/{id}")
    public Response<Void> deleteFile(@PathVariable("id") Long id) {
        this.fileService.deleteFile(id);
        return Response.success();
    }
}
