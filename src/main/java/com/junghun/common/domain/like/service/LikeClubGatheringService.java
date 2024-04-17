package com.junghun.common.domain.like.service;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.like.repository.LikeClubGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeClubGatheringService {

    private final LikeClubGatheringRepository repository;
    private final UserService userService;

}
