package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringTagUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringTagUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.ClubGatheringTag;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringTag;
import com.junghun.common.domain.gathering.repository.ClubGatheringTagRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringTagService {
    private final ClubGatheringTagRepository repository;

    public void upload(ClubGatheringTagUploadDto clubGatheringTagUploadDto) {
        for(String tag : clubGatheringTagUploadDto.getTagList()){
            ClubGatheringTag clubGatheringTag = ClubGatheringTag.builder()
                    .clubGathering(clubGatheringTagUploadDto.getClubGathering())
                    .tag(tag)
                    .build();

            repository.save(clubGatheringTag);
        }
    }

    public void deleteAll(Long clubGatheringId) {
        List<ClubGatheringTag> clubGatheringTagList = repository.findByClubGatheringId(clubGatheringId);
        for(ClubGatheringTag clubGatheringTag : clubGatheringTagList){
            repository.deleteById(clubGatheringTag.getId());
        }
    }

}
