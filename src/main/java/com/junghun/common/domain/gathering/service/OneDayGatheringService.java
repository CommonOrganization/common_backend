package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.entity.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .recruitWay(oneDayGatheringUploadDto.getRecruitWay())
                .recruitQuestion(oneDayGatheringUploadDto.getRecruitQuestion())
                .capacity(oneDayGatheringUploadDto.getCapacity())
                .timeStamp(writeDate)
                .type(oneDayGatheringUploadDto.getType())
                .openingDate(oneDayGatheringUploadDto.getOpeningDate())
                .place(oneDayGatheringUploadDto.getPlace())
                .haveEntryFee(oneDayGatheringUploadDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUploadDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUploadDto.isShowAllThePeople())
                .tagList(oneDayGatheringUploadDto.getTagList())
                .imageList(oneDayGatheringUploadDto.getImageList())
                .clubGathering(clubGathering)
                .build();

        return repository.save(gathering);
    }

    // READ
    public OneDayGathering findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 Gathering 이 존재하지 않습니다."));
    }

    public List<OneDayGathering> findByManagerId(Long managerId) {
        return repository.findByManagerId(managerId);
    }

    public List<OneDayGathering> findByClubGatheringId(Long clubGatheringId) {
        return repository.findByClubGatheringId(clubGatheringId);
    }

    public List<OneDayGathering> findByApplierId(Long applierId) {
        return repository.findByApplierId(applierId);
    }

    public List<OneDayGathering> findParticipateInGatheringByApplierId(Long applierId) {
        return repository.findParticipateInGatheringByApplierId(applierId);
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
                .recruitWay(oneDayGatheringUpdateDto.getRecruitWay())
                .recruitQuestion(oneDayGatheringUpdateDto.getRecruitQuestion())
                .capacity(oneDayGatheringUpdateDto.getCapacity())
                .timeStamp(writeDate)
                .type(oneDayGatheringUpdateDto.getType())
                .openingDate(oneDayGatheringUpdateDto.getOpeningDate())
                .place(oneDayGatheringUpdateDto.getPlace())
                .haveEntryFee(oneDayGatheringUpdateDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUpdateDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUpdateDto.isShowAllThePeople())
                .tagList(oneDayGatheringUpdateDto.getTagList())
                .imageList(oneDayGatheringUpdateDto.getImageList())
                .clubGathering(clubGathering)
                .build();

        return repository.save(updateGathering);
    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
