package com.junghun.common.domain.image.controller;

import com.junghun.common.domain.image.exception.NotSavedFileTypeException;
import com.junghun.common.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService service;

    @PostMapping("/upload/{path}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String path) {
        try {
            // 이미지 업로드 후 파일 경로를 받아옴
            String filePath = service.uploadImage(file, path);

            // 클라이언트에게 파일의 절대 경로를 반환
            return ResponseEntity.ok(filePath);
        } catch (NotSavedFileTypeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{path}/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String path, @PathVariable String imageName) {
        try {

            byte[] imageBytes = service.getImage(path, imageName);

            // ResponseEntity를 사용해 byte 배열과 함께 상태 코드, 헤더를 클라이언트에게 전송합니다.
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + imageName)
                    .contentType(MediaType.IMAGE_JPEG) // MIME 타입을 image/jpeg로 설정
                    .body(imageBytes);
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.badRequest().build();
        }
    }
}

