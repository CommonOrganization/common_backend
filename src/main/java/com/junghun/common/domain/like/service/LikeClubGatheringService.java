package com.junghun.common.domain.like.service;

import com.junghun.common.domain.like.repository.LikeClubGatheringRepository;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeClubGatheringService {

    private final LikeClubGatheringRepository repository;
    private final UserService userService;

}
