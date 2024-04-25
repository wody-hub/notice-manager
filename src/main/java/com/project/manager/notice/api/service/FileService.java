package com.project.manager.notice.api.service;

import com.project.manager.notice.api.domain.file.File;
import com.project.manager.notice.api.domain.file.FileRepository;
import com.project.manager.notice.api.exception.BadRequestException;
import com.project.manager.notice.api.exception.RequestFileNotFoundException;
import com.project.manager.notice.api.vo.FileReqVo;
import com.project.manager.notice.api.vo.FileResVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 파일 관리 Service
 *
 * @author 정재요
 * @date 2024. 04. 24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${service.file.path.home}")
    private String fileHome;

    private final FileRepository fileRepository;

    /**
     * 파일 정보 등록
     *
     * @param reqDto 파일 등록 요청 정보
     */
    @Transactional
    public List<FileResVo> saveFile(FileReqVo reqDto) {

        final var currentDirectory = this.fileHome + this.getFileDirectory();
        final var path = Paths.get(currentDirectory).toAbsolutePath().normalize();
        final var fileList = reqDto.getFiles();

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("파일 등록 중 오류가 발생하였습니다.");
                log.error(e.getMessage(), e);
            }
            throw new BadRequestException("파일 등록 중 오류가 발생하였습니다.");
        }

        // 파일 정보 등록
        if (!CollectionUtils.isEmpty(fileList)) {
            return fileList.stream()
                    .map(multipartFile -> {
                        final var file = File.builder()
                                .fileName(this.createFileName())
                                .fileOriginalName(multipartFile.getOriginalFilename())
                                .filePath(currentDirectory)
                                .build();

                        this.fileRepository.save(file);

                        // 파일 등록.
                        final var filePath = path.resolve(file.getFileName()).normalize();
                        try {
                            multipartFile.transferTo(filePath);
                        } catch (IOException e) {
                            if (log.isErrorEnabled()) {
                                log.error("파일 생성 처리 중 오류가 발생하였습니다.");
                                log.error(e.getMessage(), e);
                            }
                            throw new BadRequestException("파일 생성 처리 중 오류가 발생하였습니다.");
                        }
                        return file;
                    })
                    .map(file -> FileResVo.builder()
                            .id(file.getId())
                            .originalFilename(file.getFileOriginalName())
                            .newFilename(file.getFileName())
                            .build())
                    .toList();
        }

        return Collections.emptyList();
    }

    /**
     * 파일 정보 조회
     *
     * @param id 파일 ID
     * @return 파일 응답 정보
     */
    @Transactional(readOnly = true)
    public FileResVo findFile(Long id) {
        final var file = this.fileRepository.findById(id).orElse(null);
        if (file == null) return null;

        return FileResVo.builder()
                .id(file.getId())
                .originalFilename(file.getFileOriginalName())
                .newFilename(file.getFileName())
                .build();
    }

    /** 새로운 파일 명 생성 */
    private String createFileName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /** 파일 저장할 경로. */
    private String getFileDirectory() {
        return "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 파일 삭제 요청
     *
     * @param id 파일 ID
     */
    @Transactional
    public void deleteFile(Long id) {
        this.fileRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Resource downloadFile(Long id) {
        final var file = this.fileRepository.findById(id).orElseThrow(() -> new RequestFileNotFoundException("파일을 찾을 수 없습니다."));

        String uploadedDate = file.getCreatedDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String filename = file.getSaveName();
        Path filePath = Paths.get(uploadPath, uploadedDate, filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() == false || resource.isFile() == false) {
                throw new RuntimeException("file not found : " + filePath.toString());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("file not found : " + filePath.toString());
        }
        출처:
        https:
//congsong.tistory.com/45 [Let's develop:티스토리]
    }

}
