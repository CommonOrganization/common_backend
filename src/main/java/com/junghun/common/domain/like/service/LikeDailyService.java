package com.junghun.common.domain.like.service;

import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.like.dto.LikeDailyDto;
import com.junghun.common.domain.like.exception.AlreadyLikeException;
import com.junghun.common.domain.like.model.LikeDaily;
import com.junghun.common.domain.like.repository.LikeDailyRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDailyService {

    private final LikeDailyRepository repository;
    private final UserRepository userRepository;
    private final DailyRepository dailyRepository;

    public boolean isLike(LikeDailyDto likeDailyDto) {
        List<LikeDaily> likeList = repository.findIsAlreadyLike(likeDailyDto.getUserId(), likeDailyDto.getDailyId());
        return !likeList.isEmpty();
    }

    public LikeDaily like(LikeDailyDto likeDailyDto) {

        validAlreadyLike(likeDailyDto);

        LocalDateTime writeDate = LocalDateTime.now();

        User user = userRepository.findById(likeDailyDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException(likeDailyDto.getUserId() + "를 가진 이용자가 존재하지 않습니다."));

        Daily daily = dailyRepository.findById(likeDailyDto.getDailyId())
                .orElseThrow(() -> new NotFoundDailyException(likeDailyDto.getDailyId() + "를 가진 데일리가 존재하지 않습니다."));

        LikeDaily likeDaily = LikeDaily.builder()
                .user(user)
                .daily(daily)
                .timeStamp(writeDate)
                .build();

        return repository.save(likeDaily);
    }

    public void dislike(Long id) {
        repository.deleteById(id);
    }

    public void validAlreadyLike(LikeDailyDto likeDailyDto) {
        List<LikeDaily> likeList = repository.findIsAlreadyLike(likeDailyDto.getUserId(), likeDailyDto.getDailyId());
        if (!likeList.isEmpty()) throw new AlreadyLikeException("이미 즐겨찾기 중입니다.");
    }
}
