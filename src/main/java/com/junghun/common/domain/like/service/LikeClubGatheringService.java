package com.junghun.common.domain.like.service;

import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.model.LikeClubGathering;
import com.junghun.common.domain.like.exception.AlreadyLikeException;
import com.junghun.common.domain.like.repository.LikeClubGatheringRepository;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeClubGatheringService {

    private final LikeClubGatheringRepository repository;
    private final UserService userService;
    private final ClubGatheringService gatheringService;

    public LikeClubGathering like(LikeClubGatheringDto likeClubGatheringDto) {
        List<LikeClubGathering> likeList = repository.findByUserIdAndClubGatheringId(likeClubGatheringDto.getUserId(),likeClubGatheringDto.getGatheringId());
        if(!likeList.isEmpty()) throw new AlreadyLikeException("이미 즐겨찾기 중입니다.");

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
