package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.service.ClubGatheringApplyStatusService;
import com.junghun.common.domain.gathering.service.OneDayGatheringApplyStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubGatheringApplyStatus")
@RequiredArgsConstructor
public class ClubGatheringApplyStatusController {
    private final ClubGatheringApplyStatusService service;

    @PutMapping("/apply/{id}")
    public ResponseEntity<Void> applyGathering(@PathVariable Long id,@RequestParam Long applierId) {
        service.applyGathering(applierId,id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<Void> approveGathering(@PathVariable Long id,@RequestParam Long applierId) {
        service.approveGathering(applierId,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/refuse/{statusId}")
    public ResponseEntity<Void> refuseApplyGathering(@PathVariable Long statusId) {
        service.refuseApplyGathering(statusId);
        return ResponseEntity.ok().build();
    }
}
