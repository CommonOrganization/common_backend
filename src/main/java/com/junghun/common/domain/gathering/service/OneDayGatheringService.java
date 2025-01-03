package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.model.RecruitWay;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringApplyStatusRepository;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import com.junghun.common.util.ConvertUtils;
import com.junghun.common.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OneDayGatheringService {
    private final OneDayGatheringRepository repository;
    private final UserRepository userRepository;
    private final ClubGatheringRepository clubGatheringRepository;
    private final OneDayGatheringApplyStatusRepository oneDayGatheringApplyStatusRepository;

    // CREATE
    public OneDayGathering upload(OneDayGatheringUploadDto oneDayGatheringUploadDto) {

        User manager = userRepository.findById(oneDayGatheringUploadDto.getManagerId())
                .orElseThrow(()->new NotFoundUserException(oneDayGatheringUploadDto.getManagerId()+"를 가진 이용자가 존재하지 않습니다."));
        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (oneDayGatheringUploadDto.getClubGatheringId() != null) {
            clubGathering = clubGatheringRepository.findById(oneDayGatheringUploadDto.getClubGatheringId())
                    .orElseThrow(()->new NotFoundGatheringException(oneDayGatheringUploadDto.getClubGatheringId()+"를 가진 소모임이 존재하지 않습니다."));
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

    public List<OneDayGathering> findTodayGathering() {
        LocalDateTime nowDate = LocalDateTime.now();
        LocalDateTime endDate = nowDate.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return repository.findTodayGathering(nowDate, endDate);
    }


    public List<OneDayGathering> findRecommendGatheringWithCategories(String[] categories) {
        String randomCategory = categories[RandomUtils.getRandomIndex(categories.length)];
        return repository.recommendGatheringByCategory(randomCategory);
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
            clubGathering = clubGatheringRepository.findById(oneDayGatheringUpdateDto.getClubGatheringId())
                    .orElseThrow(()->new NotFoundGatheringException(oneDayGatheringUpdateDto.getClubGatheringId()+"를 가진 소모임이 존재하지 않습니다."));
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
        oneDayGatheringApplyStatusRepository.deleteApplyStatusByOneDayGathering(id);
        repository.deleteById(id);
    }
}
