package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.dto.*;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.gathering.service.OneDayGatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oneDay")
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

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<OneDayGathering>> findByManagerId(@PathVariable Long managerId) {
        List<OneDayGathering> gatheringList = service.findByManagerId(managerId);
        return ResponseEntity.ok(gatheringList);
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

    @GetMapping("/applier/{applierId}/true")
    public ResponseEntity<List<OneDayGathering>> findParticipateInGatheringByApplierId(@PathVariable Long applierId) {
        List<OneDayGathering> gatheringList = service.findParticipateInGatheringByApplierId(applierId);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/today")
    public ResponseEntity<List<OneDayGathering>> findToday() {
        List<OneDayGathering> gatheringList = service.findTodayGathering();
        return ResponseEntity.ok(gatheringList);
    }

    // 요청 : /categories?categories=a&categories=b&categories=c => categories = [a,b,c] 의 형식으로 들어온다.
    @GetMapping("/recommend")
    public ResponseEntity<List<OneDayGathering>> findRecommendGatheringWithCategories(@RequestParam String[] categories) {
        List<OneDayGathering> gatheringList = service.findRecommendGatheringWithCategories(categories);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/city")
    public ResponseEntity<List<OneDayGathering>> findByCity(@RequestParam String city) {
        List<OneDayGathering> gatheringList = service.findByCity(city);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/category")
    public ResponseEntity<List<OneDayGathering>> findByCategory(@RequestParam String category) {
        List<OneDayGathering> gatheringList = service.findByCategory(category);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<OneDayGathering>> findByKeyword(@RequestParam String keyword) {
        List<OneDayGathering> gatheringList = service.findByKeyword(keyword);
        return ResponseEntity.ok(gatheringList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OneDayGathering> update(@PathVariable Long id, @RequestBody OneDayGatheringUpdateDto oneDayGatheringUpdateDto) {
        OneDayGathering gathering = service.update(id, oneDayGatheringUpdateDto);
        return ResponseEntity.ok(gathering);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
