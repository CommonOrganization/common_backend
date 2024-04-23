package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.service.OneDayGatheringService;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<OneDayGathering> findById(@PathVariable Long id) {
        OneDayGathering gathering = service.findById(id);
        return ResponseEntity.ok(gathering);
    }

    @GetMapping("/clubGathering/{clubGatheringId}")
    public ResponseEntity<List<OneDayGathering>> findByClubGatheringId(@PathVariable Long clubGatheringId) {
        List<OneDayGathering> gatheringList = service.findByClubGatheringId(clubGatheringId);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/applier/{applierId}")
    public ResponseEntity<List<OneDayGathering>> findByApplierId(@PathVariable Long applierId) {
        List<OneDayGathering> gatheringList = service.findByApplierId(applierId);
        return ResponseEntity.ok(gatheringList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OneDayGathering> update(@PathVariable Long id, @RequestBody OneDayGatheringUpdateDto oneDayGatheringUpdateDto) {
        OneDayGathering gathering = service.update(id,oneDayGatheringUpdateDto);
        return ResponseEntity.ok(gathering);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
