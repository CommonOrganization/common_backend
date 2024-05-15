package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
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

    @GetMapping("/recommend")
    public ResponseEntity<List<ClubGathering>> findRecommendByCategory(@RequestParam String city,@RequestParam String category) {
        List<ClubGathering> gatheringList = service.findRecommendByCategory(city,category);
        return ResponseEntity.ok(gatheringList);
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
