package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.exception.NotFoundCommentException;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClubGatheringService {
    private final ClubGatheringRepository repository;
    private final UserService userService;

    public ClubGathering findById(Long id){
        return repository.findById(id)
                .orElseThrow(()->new NotFoundGatheringException(id+"을(를) 가진 ClubGathering 이(가) 존재하지 않습니다."));
    }
}
