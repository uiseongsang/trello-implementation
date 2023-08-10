package com.winner.trelloimplementation.aws.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    /**
     * 파일을 업로드
     *
     * @param multipartFile 멀티파트 요청에서 수신된 업로드된 파일
     * @return Url 값
     */
    String uploadFile(MultipartFile multipartFile);

    /**
     * 파일이 들어있는지 확인
     *
     * @param multipartFile 검증할 파일
     */
    void validateFile(MultipartFile multipartFile);
}
