package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.entity.ClubGatheringApplyStatus;
import com.junghun.common.domain.gathering.entity.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.service.ClubGatheringApplyStatusService;
import com.junghun.common.domain.gathering.service.OneDayGatheringApplyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubGatheringApplyStatus")
@RequiredArgsConstructor
public class ClubGatheringApplyStatusController {
    private final ClubGatheringApplyStatusService service;

    @PutMapping("/apply/{clubGatheringId}")
    public ResponseEntity<Void> applyGathering(@PathVariable Long clubGatheringId,@RequestParam Long applierId) {
        service.applyGathering(applierId,clubGatheringId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<ClubGatheringApplyStatus> getStatus(@PathVariable Long statusId) {
        ClubGatheringApplyStatus status = service.findById(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PatchMapping("/approve/{clubGatheringId}")
    public ResponseEntity<Void> approveGathering(@PathVariable Long clubGatheringId,@RequestParam Long applierId) {
        service.approveGathering(applierId,clubGatheringId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/refuse/{statusId}")
    public ResponseEntity<Void> refuseApplyGathering(@PathVariable Long statusId) {
        service.refuseApplyGathering(statusId);
        return ResponseEntity.ok().build();
    }
}
