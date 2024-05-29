package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.like.service.LikeObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeObjectController {
    private final LikeObjectService service;

    @GetMapping("/isLike")
    public ResponseEntity<Boolean> isLike(@RequestParam Long userId, @RequestParam Long objectId, @RequestParam String likeObjectType) {
        boolean isLikedObject = service.isLike(userId, objectId, likeObjectType);
        return ResponseEntity.ok(isLikedObject);
    }
}
