package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.like.dto.LikeObjectDto;
import com.junghun.common.domain.like.dto.LikeOneDayGatheringDto;
import com.junghun.common.domain.like.model.LikeObject;
import com.junghun.common.domain.like.model.LikeOneDayGathering;
import com.junghun.common.domain.like.service.LikeObjectService;
import com.junghun.common.domain.like.service.LikeOneDayGatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like/oneDay")
@RequiredArgsConstructor
public class LikeOneDayGatheringController {
    private final LikeOneDayGatheringService service;

    @GetMapping("/isLike")
    public ResponseEntity<Boolean> isLike(@RequestBody LikeOneDayGatheringDto likeOneDayGatheringDto) {
        boolean isLikedOneDayGathering = service.isLike(likeOneDayGatheringDto);
        return ResponseEntity.ok(isLikedOneDayGathering);
    }

    @PutMapping("/like")
    public ResponseEntity<LikeOneDayGathering> like(@RequestBody LikeOneDayGatheringDto likeOneDayGatheringDto) {
        LikeOneDayGathering likeOneDayGathering = service.like(likeOneDayGatheringDto);
        return ResponseEntity.ok(likeOneDayGathering);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<Void> dislike(@PathVariable Long id) {
        service.dislike(id);
        return ResponseEntity.ok().build();
    }
}
