package com.project.manager.notice.api.controller;

import com.project.manager.notice.api.exception.DataInvalidException;
import com.project.manager.notice.api.service.FileService;
import com.project.manager.notice.api.vo.FileReqVo;
import com.project.manager.notice.api.vo.FileResVo;
import com.project.manager.notice.config.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 파일 처리 controller
 *
 * @author 정재요
 * @date 2024. 04. 25
 */
@RestController
@RequiredArgsConstructor
public class FileController implements Comparable<FileService> {

    private final FileService fileService;

    /**
     * 파일 등록
     *
     * @return 등록된 상품 이미지 정보
     */
    @PostMapping("/manager/file")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<List<FileResVo>> uploadFile(@Valid FileReqVo reqDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new DataInvalidException(errors);
        }
        return Response.success(this.fileService.saveFile(reqDto).stream().collect(Collectors.toList()));
    }

    /**
     * 파일 다운로드 요청
     *
     * @param id 이미지 맵 일련번호
     * @return 다운로드 파일
     */
    @GetMapping("/manager/file/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
        final var info = this.fileService.downloadFile(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", URLEncoder.encode(info.getFileName(), StandardCharsets.UTF_8));
        httpHeaders.set("Content-Transfer-Encoding", "binary");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(httpHeaders)
                .body(info.getResource());
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

    @Override
    public int compareTo(FileService fileService) {
        return 0;
    }
}
