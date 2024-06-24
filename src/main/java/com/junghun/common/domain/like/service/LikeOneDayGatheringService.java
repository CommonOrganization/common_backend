package com.junghun.common.domain.like.service;

import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.like.dto.LikeOneDayGatheringDto;
import com.junghun.common.domain.like.exception.AlreadyLikeException;
import com.junghun.common.domain.like.model.LikeOneDayGathering;
import com.junghun.common.domain.like.repository.LikeOneDayGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeOneDayGatheringService {

    private final LikeOneDayGatheringRepository repository;
    private final UserRepository userRepository;
    private final OneDayGatheringRepository oneDayGatheringRepository;

    public boolean isLike(LikeOneDayGatheringDto likeOneDayGatheringDto) {
        List<LikeOneDayGathering> likeList = repository.findIsAlreadyLike(likeOneDayGatheringDto.getUserId(), likeOneDayGatheringDto.getOneDayGatheringId());
        return !likeList.isEmpty();
    }

    public LikeOneDayGathering like(LikeOneDayGatheringDto likeOneDayGatheringDto) {

        validAlreadyLike(likeOneDayGatheringDto);

        LocalDateTime writeDate = LocalDateTime.now();

        User user = userRepository.findById(likeOneDayGatheringDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException(likeOneDayGatheringDto.getUserId() + "를 가진 이용자가 존재하지 않습니다."));

        OneDayGathering oneDayGathering = oneDayGatheringRepository.findById(likeOneDayGatheringDto.getOneDayGatheringId())
                .orElseThrow(() -> new NotFoundGatheringException(likeOneDayGatheringDto.getOneDayGatheringId() + "를 가진 하루모임이 존재하지 않습니다."));

        LikeOneDayGathering likeGathering = LikeOneDayGathering.builder()
                .user(user)
                .oneDayGathering(oneDayGathering)
                .timeStamp(writeDate)
                .build();

        return repository.save(likeGathering);
    }

    public void dislike(Long id) {
        repository.deleteById(id);
    }

    public void validAlreadyLike(LikeOneDayGatheringDto likeOneDayGatheringDto) {
        List<LikeOneDayGathering> likeList = repository.findIsAlreadyLike(likeOneDayGatheringDto.getUserId(), likeOneDayGatheringDto.getOneDayGatheringId());
        if (!likeList.isEmpty()) throw new AlreadyLikeException("이미 즐겨찾기 중입니다.");
    }
}
