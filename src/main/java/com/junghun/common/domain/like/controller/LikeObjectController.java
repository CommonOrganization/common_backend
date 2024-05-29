package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.like.dto.LikeObjectDto;
import com.junghun.common.domain.like.model.LikeObject;
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
    public ResponseEntity<Boolean> isLike(@RequestBody LikeObjectDto likeObjectDto) {
        boolean isLikedObject = service.isLike(likeObjectDto);
        return ResponseEntity.ok(isLikedObject);
    }

    @PutMapping("/like")
    public ResponseEntity<LikeObject> like(@RequestBody LikeObjectDto likeObjectDto) {
        LikeObject likeObject = service.like(likeObjectDto);
        return ResponseEntity.ok(likeObject);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<Void> dislike(@PathVariable Long id) {
        service.dislike(id);
        return ResponseEntity.ok().build();
    }
}
