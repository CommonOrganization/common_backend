package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyImageUploadDto;
import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.entity.DailyImage;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.repository.DailyImageRepository;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyImageService {

    private final DailyImageRepository repository;


    // CREATE
    public void upload(DailyImageUploadDto dailyImageUploadDto) {
        for(String image : dailyImageUploadDto.getImageList()){
            DailyImage dailyImage = DailyImage.builder()
                    .daily(dailyImageUploadDto.getDaily())
                    .image(image)
                    .build();
            DailyImage savedImage = repository.save(dailyImage);
            log.error("-------------------------------------------- savedImage : {}",savedImage.getImage());
        }
    }

    public void deleteAll(Long dailyId) {
        List<DailyImage> dailyImageList = repository.findByDailyId(dailyId);
        for(DailyImage dailyImage : dailyImageList){
            repository.deleteById(dailyImage.getId());
        }
    }
}
