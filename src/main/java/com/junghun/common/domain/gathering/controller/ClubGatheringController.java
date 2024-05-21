package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubGathering")
@RequiredArgsConstructor
public class ClubGatheringController {
    private final ClubGatheringService service;

    @PutMapping("/upload")
    public ResponseEntity<ClubGathering> upload(@RequestBody ClubGatheringUploadDto clubGatheringUploadDto) {
        ClubGathering gathering = service.upload(clubGatheringUploadDto);
        return new ResponseEntity<>(gathering, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubGathering> findById(@PathVariable Long id) {
        ClubGathering clubGathering = service.findById(id);
        return ResponseEntity.ok(clubGathering);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<ClubGathering>> findByManagerId(@PathVariable Long managerId) {
        List<ClubGathering> gatheringList = service.findByManagerId(managerId);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/applier/{applierId}/true")
    public ResponseEntity<List<ClubGathering>> findParticipateInGatheringByApplierId(@PathVariable Long applierId) {
        List<ClubGathering> gatheringList = service.findParticipateInGatheringByApplierId(applierId);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/trend")
    public ResponseEntity<List<ClubGathering>> findTrendGathering(@RequestParam String city) {
        List<ClubGathering> gatheringList = service.findTrendGathering(city);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<ClubGathering>> findByKeyword(@RequestParam String city,@RequestParam String keyword) {
        List<ClubGathering> gatheringList = service.findByKeyword(city,keyword);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ClubGathering>> findByCategory(@RequestParam String city,@RequestParam String category) {
        List<ClubGathering> gatheringList = service.findByCategory(city,category);
        return ResponseEntity.ok(gatheringList);
    }

    @GetMapping("/ranking/filter")
    public ResponseEntity<List<String>> filterRankingCategories(@RequestParam String city,@RequestParam List<String> categories) {
        List<String> categoryList = service.filterRankingCategories(city,categories);
        return ResponseEntity.ok(categoryList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClubGathering> update(@PathVariable Long id, @RequestBody ClubGatheringUpdateDto clubGatheringUpdateDto) {
        ClubGathering gathering = service.update(id, clubGatheringUpdateDto);
        return ResponseEntity.ok(gathering);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
