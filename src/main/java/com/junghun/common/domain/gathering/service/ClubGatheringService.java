package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .imageList(clubGatheringUploadDto.getImageList())
                .tagList(clubGatheringUploadDto.getTagList())
                .cityList(clubGatheringUploadDto.getCityList())
                .build();

        return repository.save(gathering);
    }

    public ClubGathering update(Long id, ClubGatheringUpdateDto clubGatheringUpdateDto) {

        ClubGathering gathering = repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 Gathering 이 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering updateGathering = ClubGathering.builder()
                .id(id)
                .manager(gathering.getManager())
                .category(clubGatheringUpdateDto.getCategory())
                .detailCategory(clubGatheringUpdateDto.getDetailCategory())
                .title(clubGatheringUpdateDto.getTitle())
                .content(clubGatheringUpdateDto.getContent())
                .mainImage(clubGatheringUpdateDto.getMainImage())
                .recruitWay(clubGatheringUpdateDto.getRecruitWay())
                .recruitQuestion(clubGatheringUpdateDto.getRecruitQuestion())
                .capacity(clubGatheringUpdateDto.getCapacity())
                .timeStamp(writeDate)
                .imageList(clubGatheringUpdateDto.getImageList())
                .tagList(clubGatheringUpdateDto.getTagList())
                .cityList(clubGatheringUpdateDto.getCityList())
                .build();


        return repository.save(updateGathering);
    }

    public ClubGathering findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 ClubGathering 이(가) 존재하지 않습니다."));
    }

    public List<ClubGathering> findByManagerId(Long managerId) {
        return repository.findByManagerIdOrderByTimeStampDesc(managerId);
    }

    public List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId) {
        return repository.findParticipateInGatheringByApplierId(applierId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
