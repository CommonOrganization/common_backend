package com.junghun.common.global.upload.image.controller;

import com.junghun.common.global.upload.image.exception.NotFoundFileException;
import com.junghun.common.global.upload.image.exception.NotSavedFileTypeException;
import com.junghun.common.global.upload.image.service.ImageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/upload/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/{path}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String path) {
        try {
            // 이미지 업로드 후 파일 경로를 받아옴
            String filePath = imageService.uploadImage(file, path);

            // 클라이언트에게 파일의 절대 경로를 반환
            return ResponseEntity.ok(filePath);
        } catch (NotSavedFileTypeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{path}/multiple")
    public ResponseEntity<List<String>> uploadMultipleImages(@RequestParam("files") List<MultipartFile> files, @PathVariable String path) {
        try {
            // 이미지 업로드 후 파일 경로 리스트를 받아옴
            List<String> fileUrls = imageService.uploadMultipleImages(files, "/" + path);

            return ResponseEntity.ok(fileUrls);
        } catch (NotSavedFileTypeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}

