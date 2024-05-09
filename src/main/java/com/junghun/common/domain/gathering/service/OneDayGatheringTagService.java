package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringImageUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringTagUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringImage;
import com.junghun.common.domain.gathering.entity.OneDayGatheringTag;
import com.junghun.common.domain.gathering.repository.OneDayGatheringImageRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OneDayGatheringTagService {

    private final OneDayGatheringTagRepository repository;

    public void upload(OneDayGatheringTagUploadDto oneDayGatheringTagUploadDto) {
        for(String tag : oneDayGatheringTagUploadDto.getTagList()){
            OneDayGatheringTag oneDayGatheringTag = OneDayGatheringTag.builder()
                    .oneDayGathering(oneDayGatheringTagUploadDto.getOneDayGathering())
                    .tag(tag)
                    .build();

            repository.save(oneDayGatheringTag);
        }
    }

    public void deleteAll(Long oneDayGatheringId) {
        List<OneDayGatheringTag> oneDayGatheringTagList = repository.findByOneDayGatheringId(oneDayGatheringId);
        for(OneDayGatheringTag oneDayGatheringTag : oneDayGatheringTagList){
            repository.deleteById(oneDayGatheringTag.getId());
        }
    }

}
