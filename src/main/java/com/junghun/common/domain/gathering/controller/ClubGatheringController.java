package com.junghun.common.domain.gathering.controller;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubGathering")
@RequiredArgsConstructor
public class ClubGatheringController {
    private final ClubGatheringService service;

    @GetMapping("/{id}")
    public ResponseEntity<ClubGathering> findById(@PathVariable Long id) {
        ClubGathering clubGathering = service.findById(id);
        return ResponseEntity.ok(clubGathering);
    }
}
