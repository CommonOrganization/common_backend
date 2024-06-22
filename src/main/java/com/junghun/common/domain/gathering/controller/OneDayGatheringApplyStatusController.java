package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.service.OneDayGatheringApplyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apply/oneDay")
@RequiredArgsConstructor
public class OneDayGatheringApplyStatusController {
    private final OneDayGatheringApplyStatusService service;

    @PutMapping("/{gatheringId}")
    public ResponseEntity<Void> applyGathering(@PathVariable Long gatheringId, @RequestParam Long applierId) {
        service.applyGathering(applierId, gatheringId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<OneDayGatheringApplyStatus> findStatus(@PathVariable Long statusId) {
        OneDayGatheringApplyStatus status = service.findStatus(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PatchMapping("/approve/{statusId}")
    public ResponseEntity<Void> approveGathering(@PathVariable Long statusId) {
        service.approveGathering(statusId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/refuse/{statusId}")
    public ResponseEntity<Void> refuseApplyGathering(@PathVariable Long statusId) {
        service.refuseApplyGathering(statusId);
        return ResponseEntity.ok().build();
    }
}
