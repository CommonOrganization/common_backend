package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClubGatheringService {
    private final ClubGatheringRepository repository;
    private final UserService userService;

    public ClubGathering upload(ClubGatheringUploadDto clubGatheringUploadDto) {
        User manager = userService.findById(clubGatheringUploadDto.getManagerId());
        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering gathering = ClubGathering.builder()
                .manager(manager)
                .category(clubGatheringUploadDto.getCategory())
                .detailCategory(clubGatheringUploadDto.getDetailCategory())
                .title(clubGatheringUploadDto.getTitle())
                .content(clubGatheringUploadDto.getContent())
                .mainImage(clubGatheringUploadDto.getMainImage())
                .recruitWay(clubGatheringUploadDto.getRecruitWay())
                .recruitQuestion(clubGatheringUploadDto.getRecruitQuestion())
                .capacity(clubGatheringUploadDto.getCapacity())
                .timeStamp(writeDate)
                .cityList(clubGatheringUploadDto.getCityList())
                .build();

        return repository.save(gathering);
    }

//    public ClubGathering update(Long id, ClubGatheringUpdateDto clubGatheringUpdateDto) {
//
//        OneDayGathering gathering = repository.findById(id)
//                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 Gathering 이 존재하지 않습니다."));
//
//        LocalDateTime writeDate = LocalDateTime.now();
//
//        ClubGathering clubGathering = null;
//        if (oneDayGatheringUpdateDto.getClubGatheringId() != null) {
//            try {
//                clubGathering = clubGatheringService.findById(oneDayGatheringUpdateDto.getClubGatheringId());
//            } catch (NotFoundGatheringException exception) {
//                throw new NotFoundGatheringException(oneDayGatheringUpdateDto.getClubGatheringId() + " 을(를) 가진 Gathering 이 존재하지 않습니다.");
//            }
//        }
//
//
//        OneDayGathering updateGathering = OneDayGathering.builder()
//                .id(id)
//                .manager(gathering.getManager())
//                .category(oneDayGatheringUpdateDto.getCategory())
//                .detailCategory(oneDayGatheringUpdateDto.getDetailCategory())
//                .title(oneDayGatheringUpdateDto.getTitle())
//                .content(oneDayGatheringUpdateDto.getContent())
//                .mainImage(oneDayGatheringUpdateDto.getMainImage())
//                .recruitWay(oneDayGatheringUpdateDto.getRecruitWay())
//                .recruitQuestion(oneDayGatheringUpdateDto.getRecruitQuestion())
//                .capacity(oneDayGatheringUpdateDto.getCapacity())
//                .timeStamp(writeDate)
//                .type(oneDayGatheringUpdateDto.getType())
//                .openingDate(oneDayGatheringUpdateDto.getOpeningDate())
//                .haveEntryFee(oneDayGatheringUpdateDto.isHaveEntryFee())
//                .entryFee(oneDayGatheringUpdateDto.getEntryFee())
//                .showAllThePeople(oneDayGatheringUpdateDto.isShowAllThePeople())
//                .tagList(oneDayGatheringUpdateDto.getTagList())
//                .imageList(oneDayGatheringUpdateDto.getImageList())
//                .clubGathering(clubGathering)
//                .build();
//
//
//        return repository.save(updateGathering);
//    }

    public ClubGathering findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 ClubGathering 이(가) 존재하지 않습니다."));
    }
}
