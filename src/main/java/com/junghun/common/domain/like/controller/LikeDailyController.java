package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.dto.LikeDailyDto;
import com.junghun.common.domain.like.model.LikeClubGathering;
import com.junghun.common.domain.like.model.LikeDaily;
import com.junghun.common.domain.like.service.LikeClubGatheringService;
import com.junghun.common.domain.like.service.LikeDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like/daily")
@RequiredArgsConstructor
public class LikeDailyController {
    private final LikeDailyService service;

    @GetMapping("/isLike")
    public ResponseEntity<Boolean> isLike(@RequestBody LikeDailyDto likeDailyDto) {
        boolean isLikedDaily = service.isLike(likeDailyDto);
        return ResponseEntity.ok(isLikedDaily);
    }

    @PutMapping("/like")
    public ResponseEntity<LikeDaily> like(@RequestBody LikeDailyDto likeDailyDto) {
        LikeDaily likeDaily = service.like(likeDailyDto);
        return ResponseEntity.ok(likeDaily);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<Void> dislike(@PathVariable Long id) {
        service.dislike(id);
        return ResponseEntity.ok().build();
    }
}
