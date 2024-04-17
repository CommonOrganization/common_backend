package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.service.OneDayGatheringService;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/oneDayGathering")
@RequiredArgsConstructor
public class OneDayGatheringController {
    private final OneDayGatheringService service;

    @PutMapping("/upload")
    public ResponseEntity<OneDayGathering> upload(@RequestBody OneDayGatheringUploadDto oneDayGatheringUploadDto) {
        OneDayGathering gathering = service.upload(oneDayGatheringUploadDto);
        return new ResponseEntity<>(gathering, HttpStatus.CREATED);
    }
}
