package com.winner.trelloimplementation.aws.controller;

import com.winner.trelloimplementation.aws.service.AwsS3Service;
import com.winner.trelloimplementation.aws.service.AwsS3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3ServiceImpl awsS3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFile(multipartFile);
    }
}
