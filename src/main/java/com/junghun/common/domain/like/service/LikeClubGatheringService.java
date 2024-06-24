package com.junghun.common.domain.like.service;

import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.exception.AlreadyLikeException;
import com.junghun.common.domain.like.model.LikeClubGathering;
import com.junghun.common.domain.like.repository.LikeClubGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeClubGatheringService {

    private final LikeClubGatheringRepository repository;
    private final UserRepository userRepository;
    private final ClubGatheringRepository clubGatheringRepository;

    public boolean isLike(LikeClubGatheringDto likeClubGatheringDto) {
        List<LikeClubGathering> likeList = repository.findIsAlreadyLike(likeClubGatheringDto.getUserId(), likeClubGatheringDto.getClubGatheringId());
        return !likeList.isEmpty();
    }

    public LikeClubGathering like(LikeClubGatheringDto likeClubGatheringDto) {

        validAlreadyLike(likeClubGatheringDto);

        LocalDateTime writeDate = LocalDateTime.now();

        User user = userRepository.findById(likeClubGatheringDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException(likeClubGatheringDto.getUserId() + "를 가진 이용자가 존재하지 않습니다."));

        ClubGathering clubGathering = clubGatheringRepository.findById(likeClubGatheringDto.getClubGatheringId())
                .orElseThrow(() -> new NotFoundGatheringException(likeClubGatheringDto.getClubGatheringId() + "를 가진 소모임이 존재하지 않습니다."));

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

    public void validAlreadyLike(LikeClubGatheringDto likeClubGatheringDto) {
        List<LikeClubGathering> likeList = repository.findIsAlreadyLike(likeClubGatheringDto.getUserId(), likeClubGatheringDto.getClubGatheringId());
        if (!likeList.isEmpty()) throw new AlreadyLikeException("이미 즐겨찾기 중입니다.");
    }
}
