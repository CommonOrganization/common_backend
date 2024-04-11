package com.junghun.common.global.converter.upload.image.service;

import com.junghun.common.global.converter.upload.image.exception.NotFoundFileException;
import com.junghun.common.global.converter.upload.image.exception.NotSavedFileTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${UPLOAD_DIR}")
    private String UPLOAD_DIR;

    @Value("HOST")
    private String HOST;

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

            String resultPath = HOST+"api/image/" + path + "/" + fileName;

            log.info("이미지 저장 완료 " + resultPath);
            // 업로드된 파일의 절대 경로를 반환
            return resultPath;
        } catch (IOException exception) {
            log.error("저장 불가");
            throw new NotSavedFileTypeException("잘못된 타입의 파일입니다.");
        }
    }

    public byte[] getImage(String path, String imageName) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/images/" + path + "/" + imageName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } catch (IOException exception) {
            throw new NotFoundFileException("존재하지 않는 파일입니다.");
        }
    }
}
