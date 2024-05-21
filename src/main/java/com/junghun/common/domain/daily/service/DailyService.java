package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.service.ClubGatheringService;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import com.junghun.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository repository;

    private final UserService userService;
    private final ClubGatheringService clubGatheringService;

    // CREATE
    public Daily upload(DailyUploadDto dailyUploadDto) {
        User writer = userService.findById(dailyUploadDto.getWriterId());

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (dailyUploadDto.getClubGatheringId() != null) {
            try {
                clubGathering = clubGatheringService.findById(dailyUploadDto.getClubGatheringId());
            } catch (NotFoundGatheringException exception) {
                throw new NotFoundGatheringException(dailyUploadDto.getClubGatheringId() + " 을(를) 가진 Gathering 이 존재하지 않습니다.");
            }
        }

        Daily daily = Daily.builder()
                .writer(writer)
                .category(dailyUploadDto.getCategory())
                .detailCategory(dailyUploadDto.getDetailCategory())
                .dailyType(dailyUploadDto.getDailyType())
                .mainImage(dailyUploadDto.getMainImage())
                .content(dailyUploadDto.getContent())
                .timeStamp(writeDate)
                .imageList(ConvertUtils.getStringByList(dailyUploadDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(dailyUploadDto.getTagList()))
                .clubGathering(clubGathering)
                .build();

        return repository.save(daily);
    }

    // READ
    public List<Daily> findAll() {
        return repository.findByOrderByTimeStampDesc();
    }

    public Daily findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
    }

    public List<Daily> findByWriterId(Long writerId) {
        return repository.findByWriterIdOrderByTimeStampDesc(writerId);
    }

    public List<Daily> findByClubGatheringId(Long gatheringId) {
        return repository.findByClubGatheringIdOrderByTimeStampDesc(gatheringId);
    }

    public List<Daily> findByKeyword(String keyword) {
        return repository.findByKeywordByTimeStampDesc(keyword);
    }

    public List<Daily> findByCategory(String category) {
        return repository.findByCategoryOrderByTimeStampDesc(category);
    }

    // UPDATE
    public Daily update(Long id, DailyUpdateDto dailyUpdateDto) {
        Daily daily = repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (dailyUpdateDto.getClubGatheringId() != null) {
            try {
                clubGathering = clubGatheringService.findById(dailyUpdateDto.getClubGatheringId());
            } catch (NotFoundGatheringException exception) {
                throw new NotFoundGatheringException(dailyUpdateDto.getClubGatheringId() + " 을(를) 가진 Gathering 이 존재하지 않습니다.");
            }
        }

        Daily updateDaily = Daily.builder()
                .id(id)
                .writer(daily.getWriter())
                .category(dailyUpdateDto.getCategory())
                .detailCategory(dailyUpdateDto.getDetailCategory())
                .dailyType(dailyUpdateDto.getDailyType())
                .mainImage(dailyUpdateDto.getMainImage())
                .content(dailyUpdateDto.getContent())
                .timeStamp(writeDate)
                .imageList(ConvertUtils.getStringByList(dailyUpdateDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(dailyUpdateDto.getTagList()))
                .clubGathering(clubGathering)
                .build();

        return repository.save(updateDaily);
    }

    // DELETE
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
        repository.deleteById(id);
    }


}
