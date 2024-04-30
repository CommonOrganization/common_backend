package com.junghun.common.domain.user.service;

import com.junghun.common.domain.user.dto.UserPlaceDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.entity.UserPlace;
import com.junghun.common.domain.user.exception.NotFoundUserPlaceException;
import com.junghun.common.domain.user.repository.UserPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPlaceService {

    private final UserService userService;
    private final UserPlaceRepository repository;

    public UserPlace upload(Long userId, UserPlaceDto userPlaceDto) {

        User user = userService.findById(userId);

        UserPlace place = UserPlace.builder()
                .user(user)
                .city(userPlaceDto.getCity())
                .middlePlace(userPlaceDto.getMiddlePlace())
                .detailPlace(userPlaceDto.getDetailPlace())
                .build();

        return repository.save(place);
    }

    public UserPlace update(Long userId, UserPlaceDto userPlaceDto) {

        UserPlace place = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserPlaceException(userId + "을(를) 가진 UserPlace 가 존재하지 않습니다."));

        User user = userService.findById(userId);

        UserPlace updatePlace = UserPlace.builder()
                .id(place.getId())
                .user(user)
                .city(userPlaceDto.getCity())
                .middlePlace(userPlaceDto.getMiddlePlace())
                .detailPlace(userPlaceDto.getDetailPlace())
                .build();

        return repository.save(updatePlace);
    }

}
