package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringImageUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringImageUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.ClubGatheringImage;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringImage;
import com.junghun.common.domain.gathering.repository.ClubGatheringImageRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringImageService {
    private final ClubGatheringImageRepository repository;

    public void upload(ClubGatheringImageUploadDto clubGatheringImageUploadDto) {
        for(String image : clubGatheringImageUploadDto.getImageList()){
            ClubGatheringImage clubGatheringImage = ClubGatheringImage.builder()
                    .clubGathering(clubGatheringImageUploadDto.getClubGathering())
                    .image(image)
                    .build();

            repository.save(clubGatheringImage);
        }
    }

    public void deleteAll(Long clubGatheringId) {
        List<ClubGatheringImage> clubGatheringImageList = repository.findByClubGatheringId(clubGatheringId);
        for(ClubGatheringImage clubGatheringImage : clubGatheringImageList){
            repository.deleteById(clubGatheringImage.getId());
        }
    }

}
