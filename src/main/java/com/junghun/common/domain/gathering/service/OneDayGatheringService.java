package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
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

    public OneDayGathering upload(OneDayGatheringUploadDto oneDayGatheringUploadDto) {

        User manager = userService.findById(oneDayGatheringUploadDto.getManagerId());
        LocalDateTime writeDate = LocalDateTime.now();

        OneDayGathering gathering =  OneDayGathering.builder()
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
                .build();

        gathering.setTagList(oneDayGatheringUploadDto.getTagList());
        gathering.setImageList(oneDayGatheringUploadDto.getImageList());

        if(oneDayGatheringUploadDto.getClubGatheringId() != null){
            try{
                ClubGathering clubGathering = clubGatheringService.findById(oneDayGatheringUploadDto.getClubGatheringId());
                gathering.setClubGathering(clubGathering);
            }catch (NotFoundGatheringException exception){
                throw new NotFoundGatheringException(oneDayGatheringUploadDto.getClubGatheringId()+" 을(를) 가진 Gathering 이 존재하지 않습니다.");
            }
        }

        return repository.save(gathering);
    }
}
