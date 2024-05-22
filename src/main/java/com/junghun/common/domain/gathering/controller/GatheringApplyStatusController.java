package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.model.GatheringApplyStatus;
import com.junghun.common.domain.gathering.model.GatheringType;
import com.junghun.common.domain.gathering.service.GatheringApplyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/GatheringApplyStatus")
@RequiredArgsConstructor
public class GatheringApplyStatusController {
    private final GatheringApplyStatusService service;

    @PutMapping("/apply/{gatheringId}")
    public ResponseEntity<Void> applyGathering(@PathVariable Long gatheringId, @RequestParam Long applierId, @RequestParam GatheringType gatheringType) {
        service.applyGathering(applierId, gatheringId,gatheringType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<GatheringApplyStatus> getStatus(@PathVariable Long statusId) {
        GatheringApplyStatus status = service.findById(statusId);
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
