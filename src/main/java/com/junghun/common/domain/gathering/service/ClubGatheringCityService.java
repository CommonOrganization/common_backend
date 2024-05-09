package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringCityUploadDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringImageUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.ClubGatheringCity;
import com.junghun.common.domain.gathering.entity.ClubGatheringImage;
import com.junghun.common.domain.gathering.repository.ClubGatheringCityRepository;
import com.junghun.common.domain.gathering.repository.ClubGatheringImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringCityService {
    private final ClubGatheringCityRepository repository;

    public void upload(ClubGatheringCityUploadDto clubGatheringCityUploadDto) {
        for(String city :clubGatheringCityUploadDto .getCityList()){
            ClubGatheringCity clubGatheringImage = ClubGatheringCity.builder()
                    .clubGathering(clubGatheringCityUploadDto.getClubGathering())
                    .city(city)
                    .build();

            repository.save(clubGatheringImage);
        }
    }

    public void deleteAll(Long clubGatheringId) {
        List<ClubGatheringCity> clubGatheringCityList = repository.findByClubGatheringId(clubGatheringId);
        for(ClubGatheringCity clubGatheringCity : clubGatheringCityList){
            repository.deleteById(clubGatheringCity.getId());
        }
    }

}
