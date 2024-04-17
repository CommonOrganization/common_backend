package com.junghun.common.domain.like.controller;

import com.junghun.common.domain.like.service.LikeClubGatheringService;
import com.junghun.common.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/like/clubGathering")
@RequiredArgsConstructor
public class LikeClubGatheringController {

    private final LikeClubGatheringService service;

}
