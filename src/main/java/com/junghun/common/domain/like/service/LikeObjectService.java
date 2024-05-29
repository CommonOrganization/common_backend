package com.junghun.common.domain.like.service;

import com.junghun.common.domain.like.dto.LikeObjectDto;
import com.junghun.common.domain.like.exception.AlreadyLikeException;
import com.junghun.common.domain.like.model.LikeObject;
import com.junghun.common.domain.like.model.LikeObjectType;
import com.junghun.common.domain.like.repository.LikeObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeObjectService {

    private final LikeObjectRepository repository;

    public boolean isLike(LikeObjectDto likeObjectDto) {
        List<LikeObject> likeList = repository.findIsAlreadyLike(likeObjectDto.getUserId(), likeObjectDto.getObjectId(), likeObjectDto.getObjectType());
        return !likeList.isEmpty();
    }

    public LikeObject like(LikeObjectDto likeObjectDto) {

        validAlreadyLike(likeObjectDto);

        LocalDateTime writeDate = LocalDateTime.now();

        LikeObject likeGathering = LikeObject.builder()
                .userId(likeObjectDto.getUserId())
                .objectId(likeObjectDto.getObjectId())
                .objectType(likeObjectDto.getObjectType())
                .timeStamp(writeDate)
                .build();

        return repository.save(likeGathering);
    }

    public void dislike(Long id) {
        repository.deleteById(id);
    }

    public void validAlreadyLike(LikeObjectDto likeObjectDto) {
        List<LikeObject> likeList = repository.findIsAlreadyLike(likeObjectDto.getUserId(), likeObjectDto.getObjectId(), likeObjectDto.getObjectType());
        if (!likeList.isEmpty()) throw new AlreadyLikeException("이미 즐겨찾기 중입니다.");
    }
}
