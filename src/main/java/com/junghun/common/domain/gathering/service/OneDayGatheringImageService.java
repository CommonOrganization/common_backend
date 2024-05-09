package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringImageUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringImage;
import com.junghun.common.domain.gathering.repository.OneDayGatheringImageRepository;
import com.junghun.common.domain.user.dto.UserCategoryDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.entity.UserCategory;
import com.junghun.common.domain.user.repository.UserCategoryRepository;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OneDayGatheringImageService {
    private final OneDayGatheringImageRepository repository;

    public void upload(OneDayGatheringImageUploadDto oneDayGatheringImageUploadDto) {
        for(String image : oneDayGatheringImageUploadDto.getImageList()){
            OneDayGatheringImage oneDayGatheringImage = OneDayGatheringImage.builder()
                    .oneDayGathering(oneDayGatheringImageUploadDto.getOneDayGathering())
                    .image(image)
                    .build();

            repository.save(oneDayGatheringImage);
        }
    }

    public void deleteAll(Long oneDayGatheringId) {
        List<OneDayGatheringImage> oneDayGatheringImageList = repository.findByOneDayGatheringId(oneDayGatheringId);
        for(OneDayGatheringImage oneDayGatheringImage : oneDayGatheringImageList){
            repository.deleteById(oneDayGatheringImage.getId());
        }
    }

}
