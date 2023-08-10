package com.winner.trelloimplementation.aws.controller;

import com.winner.trelloimplementation.aws.service.AwsS3ServiceImpl;
import com.winner.trelloimplementation.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3ServiceImpl awsS3Service;

    @PostMapping("/files")
    public ResponseEntity<ApiResponseDto> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        return ResponseEntity.ok().body(
                new ApiResponseDto(awsS3Service.uploadFile(multipartFile),HttpStatus.OK.value()));
    }

    @PostMapping("/multi-files")
    public ResponseEntity<List<String>> uploadFiles(@RequestPart(value = "files") List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(awsS3Service.uploadFiles(multipartFile));
    }

    @DeleteMapping("/files")
    public ResponseEntity<ApiResponseDto> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseDto("파일 삭제가 되었습니다", HttpStatus.OK.value()));
    }
}
