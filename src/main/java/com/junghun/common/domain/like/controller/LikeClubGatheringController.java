package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.model.LikeClubGathering;
import com.junghun.common.domain.like.service.LikeClubGatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like/club")
@RequiredArgsConstructor
public class LikeClubGatheringController {
    private final LikeClubGatheringService service;

    @GetMapping("/isLike")
    public ResponseEntity<Boolean> isLike(@RequestBody LikeClubGatheringDto likeClubGatheringDto) {
        boolean isLikedClubGathering = service.isLike(likeClubGatheringDto);
        return ResponseEntity.ok(isLikedClubGathering);
    }

    @PutMapping("/like")
    public ResponseEntity<LikeClubGathering> like(@RequestBody LikeClubGatheringDto likeClubGatheringDto) {
        LikeClubGathering likeClubGathering = service.like(likeClubGatheringDto);
        return ResponseEntity.ok(likeClubGathering);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<Void> dislike(@PathVariable Long id) {
        service.dislike(id);
        return ResponseEntity.ok().build();
    }
}
