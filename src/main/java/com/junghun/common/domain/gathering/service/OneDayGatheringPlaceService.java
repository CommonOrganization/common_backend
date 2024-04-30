package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringPlaceDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringPlace;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringPlaceException;
import com.junghun.common.domain.gathering.repository.OneDayGatheringPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneDayGatheringPlaceService {

    private final OneDayGatheringService gatheringService;
    private final OneDayGatheringPlaceRepository repository;

    public OneDayGatheringPlace upload(Long gatheringId, OneDayGatheringPlaceDto oneDayGatheringPlaceDto) {

        OneDayGathering gathering = gatheringService.findById(gatheringId);

        OneDayGatheringPlace place = OneDayGatheringPlace.builder()
                .oneDayGathering(gathering)
                .city(oneDayGatheringPlaceDto.getCity())
                .middlePlace(oneDayGatheringPlaceDto.getMiddlePlace())
                .detailPlace(oneDayGatheringPlaceDto.getDetailPlace())
                .build();

        return repository.save(place);
    }

    public OneDayGatheringPlace update(Long gatheringId, OneDayGatheringPlaceDto oneDayGatheringPlaceDto) {

        OneDayGatheringPlace place = repository.findByOneDayGatheringId(gatheringId)
                .orElseThrow(() -> new NotFoundGatheringPlaceException(gatheringId + "을(를) 가진 GatheringPlace 가 존재하지 않습니다."));

        OneDayGathering gathering = gatheringService.findById(gatheringId);

        OneDayGatheringPlace updatePlace = OneDayGatheringPlace.builder()
                .id(place.getId())
                .oneDayGathering(gathering)
                .city(oneDayGatheringPlaceDto.getCity())
                .middlePlace(oneDayGatheringPlaceDto.getMiddlePlace())
                .detailPlace(oneDayGatheringPlaceDto.getDetailPlace())
                .build();

        return repository.save(updatePlace);
    }

}
