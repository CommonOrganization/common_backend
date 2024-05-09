package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyImageUploadDto;
import com.junghun.common.domain.daily.dto.DailyTagUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.entity.DailyImage;
import com.junghun.common.domain.daily.entity.DailyTag;
import com.junghun.common.domain.daily.repository.DailyImageRepository;
import com.junghun.common.domain.daily.repository.DailyTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyTagService {

    private final DailyTagRepository repository;

    // CREATE
    public void upload(DailyTagUploadDto dailyTagUploadDto) {
        for(String tag : dailyTagUploadDto.getTagList()){
            DailyTag dailyTag = DailyTag.builder()
                    .daily(dailyTagUploadDto.getDaily())
                    .tag(tag)
                    .build();
            repository.save(dailyTag);
        }
    }

    public void deleteAll(Long dailyId) {
        List<DailyTag> dailyTagList = repository.findByDailyId(dailyId);
        for(DailyTag dailyTag : dailyTagList){
            repository.deleteById(dailyTag.getId());
        }
    }
}
