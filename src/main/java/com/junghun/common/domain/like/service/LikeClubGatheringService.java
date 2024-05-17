package com.junghun.common.domain.like.service;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.like.repository.LikeClubGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeClubGatheringService {

    private final LikeClubGatheringRepository repository;
    private final UserService userService;
    private final ClubGatheringService gatheringService;

    public LikeClubGathering like(LikeClubGatheringDto likeClubGatheringDto) {
        repository.

        User user = userService.findById(likeClubGatheringDto.getUserId());
        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = gatheringService.findById(likeClubGatheringDto.getGatheringId());

        LikeClubGathering likeGathering = LikeClubGathering.builder()
                .user(user)
                .clubGathering(clubGathering)
                .timeStamp(writeDate)
                .build();

        return repository.save(likeGathering);
    }

    public void dislike(Long id) {
        repository.deleteById(id);
    }

}
