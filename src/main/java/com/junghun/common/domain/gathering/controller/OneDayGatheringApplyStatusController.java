package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.service.OneDayGatheringApplyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/oneDayGatheringApplyStatus")
@RequiredArgsConstructor
public class OneDayGatheringApplyStatusController {
    private final OneDayGatheringApplyStatusService service;

    @PutMapping("/apply/{oneDayGatheringId}")
    public ResponseEntity<Void> applyGathering(@PathVariable Long oneDayGatheringId, @RequestParam Long applierId) {
        service.applyGathering(applierId, oneDayGatheringId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<OneDayGatheringApplyStatus> getStatus(@PathVariable Long statusId) {
        OneDayGatheringApplyStatus status = service.findById(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PatchMapping("/approve/{oneDayGatheringId}")
    public ResponseEntity<Void> approveGathering(@PathVariable Long oneDayGatheringId, @RequestParam Long applierId) {
        service.approveGathering(applierId, oneDayGatheringId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/refuse/{statusId}")
    public ResponseEntity<Void> refuseApplyGathering(@PathVariable Long statusId) {
        service.refuseApplyGathering(statusId);
        return ResponseEntity.ok().build();
    }
}
