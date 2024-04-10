package com.junghun.common.global.upload.image.service;

import com.junghun.common.global.upload.image.exception.NotFoundFileException;
import com.junghun.common.global.upload.image.exception.NotSavedFileTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public String uploadImage(MultipartFile file, String path) {
        try {
            // 업로드할 디렉토리 경로 생성
            byte[] imageBytes = file.getBytes();

            // 파일을 업로드 디렉토리에 저장
            String fileName = UUID.randomUUID() + ".jpg";
            String uploadDir = UPLOAD_DIR + path + "/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filePath = uploadDir + fileName;

            try (FileOutputStream stream = new FileOutputStream(filePath)) {
                stream.write(imageBytes);
            }

            String resultPath = "http://localhost:8080/images/"+path+"/"+fileName;

            log.info("이미지 저장 완료 " + resultPath);
            // 업로드된 파일의 절대 경로를 반환
            return resultPath;
        } catch (IOException exception) {
            log.error("저장 불가");
            throw new NotSavedFileTypeException("잘못된 타입의 파일입니다.");
        }
    }

    public List<String> uploadMultipleImages(List<MultipartFile> files, String path) {
        try {
            List<String> fileUrls = new ArrayList<>();

            Path uploadPath = Paths.get(UPLOAD_DIR + path);
            Files.createDirectories(uploadPath);

            for (MultipartFile file : files) {
                // 원본 파일명 가져오기
                String originalFilename = file.getOriginalFilename();

                // 파일을 업로드 디렉토리에 저장
                Path filePath = uploadPath.resolve(originalFilename);
                file.transferTo(filePath);

                // 업로드된 파일의 절대 경로를 리스트에 추가
                fileUrls.add(filePath.toString());
            }

            return fileUrls;
        } catch (IOException exception) {
            throw new NotSavedFileTypeException("잘못된 타입의 파일입니다.");
        }
    }
}
