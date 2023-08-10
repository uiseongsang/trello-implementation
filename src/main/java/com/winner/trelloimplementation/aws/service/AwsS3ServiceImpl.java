package com.winner.trelloimplementation.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.winner.trelloimplementation.common.s3.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service{
    private final AmazonS3Client amazonS3Client;
    private final Long MAX_UPLOAD_SIZE = 100000000L;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws MaxUploadSizeExceededException {
        if(multipartFile.getSize() > MAX_UPLOAD_SIZE) {
            throw new MaxUploadSizeExceededException(MAX_UPLOAD_SIZE);
        }

        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        // Amazon S3에 저장되는 객체 메타데이터를 나타냅니다.
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        // 파일의 내용을 읽을 InputStream
        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject( new PutObjectRequest(bucketName, fileName,inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드가 실패하였습니다");
        }
//        return amazonS3Client.getUrl(bucketName,fileName).toString();
        return fileName;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> multipartFile) {
        List<String> fileNameList = new ArrayList<>();

        multipartFile.forEach(file -> {
            if(file.getSize() > MAX_UPLOAD_SIZE) {
                throw new MaxUploadSizeExceededException(MAX_UPLOAD_SIZE);
            }

            String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject( new PutObjectRequest(bucketName, fileName,inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드가 실패하였습니다");
            }

            fileNameList.add(fileName);
        });
        return fileNameList;
    }

    @Override
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    @Override
    public void validateFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다");
        }
    }
}
