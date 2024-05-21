package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.model.RecruitWay;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import com.junghun.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OneDayGatheringService {
    private final OneDayGatheringRepository repository;
    private final UserService userService;
    private final ClubGatheringService clubGatheringService;

    // CREATE
    public OneDayGathering upload(OneDayGatheringUploadDto oneDayGatheringUploadDto) {

        User manager = userService.findById(oneDayGatheringUploadDto.getManagerId());
        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (oneDayGatheringUploadDto.getClubGatheringId() != null) {
            try {
                clubGathering = clubGatheringService.findById(oneDayGatheringUploadDto.getClubGatheringId());
            } catch (NotFoundGatheringException exception) {
                throw new NotFoundGatheringException(oneDayGatheringUploadDto.getClubGatheringId() + " 을(를) 가진 Gathering 이 존재하지 않습니다.");
            }
        }

        OneDayGathering gathering = OneDayGathering.builder()
                .manager(manager)
                .category(oneDayGatheringUploadDto.getCategory())
                .detailCategory(oneDayGatheringUploadDto.getDetailCategory())
                .title(oneDayGatheringUploadDto.getTitle())
                .content(oneDayGatheringUploadDto.getContent())
                .mainImage(oneDayGatheringUploadDto.getMainImage())
                .recruitWay(RecruitWay.valueOf(oneDayGatheringUploadDto.getRecruitWay()))
                .recruitQuestion(oneDayGatheringUploadDto.getRecruitQuestion())
                .capacity(oneDayGatheringUploadDto.getCapacity())
                .timeStamp(writeDate)
                .type(oneDayGatheringUploadDto.getType())
                .openingDate(oneDayGatheringUploadDto.getOpeningDate())
                .haveEntryFee(oneDayGatheringUploadDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUploadDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUploadDto.isShowAllThePeople())
                .clubGathering(clubGathering)
                .imageList(ConvertUtils.getStringByList(oneDayGatheringUploadDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(oneDayGatheringUploadDto.getTagList()))
                .location(ConvertUtils.getStringByMap(oneDayGatheringUploadDto.getLocation()))
                .build();

        return repository.save(gathering);
    }

    // READ
    public OneDayGathering findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 Gathering 이 존재하지 않습니다."));
    }

    public List<OneDayGathering> findByManagerId(Long managerId) {
        return repository.findByManagerIdOrderByTimeStampDesc(managerId);
    }

    public List<OneDayGathering> findByClubGatheringId(Long clubGatheringId) {
        return repository.findByClubGatheringIdOrderByTimeStampDesc(clubGatheringId);
    }

    public List<OneDayGathering> findByApplierId(Long applierId) {
        return repository.findByApplierId(applierId);
    }

    public List<OneDayGathering> findParticipateInGatheringByApplierId(Long applierId) {
        return repository.findParticipateInGatheringByApplierId(applierId);
    }

    public List<OneDayGathering> findWithToday() {
        return repository.findWithDateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
    }

    public List<OneDayGathering> findWithSoon() {
        return repository.findWithDateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }

    public List<OneDayGathering> findByCategoryIn(String[] categories) {
        return repository.findByCategoriesIn(categories);
    }

    public List<OneDayGathering> findByCity(String city) {
        return repository.findByCity(city);
    }

    public List<OneDayGathering> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<OneDayGathering> findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    // UPDATE
    public OneDayGathering update(Long id, OneDayGatheringUpdateDto oneDayGatheringUpdateDto) {

        OneDayGathering gathering = repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 Gathering 이 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (oneDayGatheringUpdateDto.getClubGatheringId() != null) {
            try {
                clubGathering = clubGatheringService.findById(oneDayGatheringUpdateDto.getClubGatheringId());
            } catch (NotFoundGatheringException exception) {
                throw new NotFoundGatheringException(oneDayGatheringUpdateDto.getClubGatheringId() + " 을(를) 가진 Gathering 이 존재하지 않습니다.");
            }
        }


        OneDayGathering updateGathering = OneDayGathering.builder()
                .id(id)
                .manager(gathering.getManager())
                .category(oneDayGatheringUpdateDto.getCategory())
                .detailCategory(oneDayGatheringUpdateDto.getDetailCategory())
                .title(oneDayGatheringUpdateDto.getTitle())
                .content(oneDayGatheringUpdateDto.getContent())
                .mainImage(oneDayGatheringUpdateDto.getMainImage())
                .recruitWay(RecruitWay.valueOf(oneDayGatheringUpdateDto.getRecruitWay()))
                .recruitQuestion(oneDayGatheringUpdateDto.getRecruitQuestion())
                .capacity(oneDayGatheringUpdateDto.getCapacity())
                .timeStamp(writeDate)
                .type(oneDayGatheringUpdateDto.getType())
                .openingDate(oneDayGatheringUpdateDto.getOpeningDate())
                .haveEntryFee(oneDayGatheringUpdateDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUpdateDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUpdateDto.isShowAllThePeople())
                .clubGathering(clubGathering)
                .imageList(ConvertUtils.getStringByList(oneDayGatheringUpdateDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(oneDayGatheringUpdateDto.getTagList()))
                .location(ConvertUtils.getStringByMap(oneDayGatheringUpdateDto.getLocation()))
                .build();

        return repository.save(updateGathering);


    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
