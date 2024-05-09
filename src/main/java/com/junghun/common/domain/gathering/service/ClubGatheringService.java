package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.*;
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

    private final ClubGatheringImageService imageService;
    private final ClubGatheringTagService tagService;
    private final ClubGatheringCityService cityService;

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
                .build();

        ClubGathering savedClubGathering =  repository.save(gathering);

        ClubGatheringImageUploadDto clubGatheringImageUploadDto = new ClubGatheringImageUploadDto();
        clubGatheringImageUploadDto.setClubGathering(savedClubGathering);
        clubGatheringImageUploadDto.setImageList(clubGatheringUploadDto.getImageList());

        ClubGatheringTagUploadDto clubGatheringTagUploadDto = new ClubGatheringTagUploadDto();
        clubGatheringTagUploadDto.setClubGathering(savedClubGathering);
        clubGatheringTagUploadDto.setTagList(clubGatheringUploadDto.getTagList());

        ClubGatheringCityUploadDto clubGatheringCityUploadDto = new ClubGatheringCityUploadDto();
        clubGatheringCityUploadDto.setClubGathering(savedClubGathering);
        clubGatheringCityUploadDto.setCityList(clubGatheringUploadDto.getCityList());

        imageService.upload(clubGatheringImageUploadDto);
        tagService.upload(clubGatheringTagUploadDto);
        cityService.upload(clubGatheringCityUploadDto);

        return savedClubGathering;
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
                .build();

        ClubGatheringImageUploadDto clubGatheringImageUploadDto = new ClubGatheringImageUploadDto();
        clubGatheringImageUploadDto.setClubGathering(updateGathering);
        clubGatheringImageUploadDto.setImageList(clubGatheringUpdateDto.getImageList());

        ClubGatheringTagUploadDto clubGatheringTagUploadDto = new ClubGatheringTagUploadDto();
        clubGatheringTagUploadDto.setClubGathering(updateGathering);
        clubGatheringTagUploadDto.setTagList(clubGatheringUpdateDto.getTagList());

        ClubGatheringCityUploadDto clubGatheringCityUploadDto = new ClubGatheringCityUploadDto();
        clubGatheringCityUploadDto.setClubGathering(updateGathering);
        clubGatheringCityUploadDto.setCityList(clubGatheringUpdateDto.getCityList());

        imageService.deleteAll(id);
        imageService.upload(clubGatheringImageUploadDto);

        tagService.deleteAll(id);
        tagService.upload(clubGatheringTagUploadDto);

        cityService.deleteAll(id);
        cityService.upload(clubGatheringCityUploadDto);

        return repository.save(updateGathering);
    }

    public ClubGathering findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundGatheringException(id + "을(를) 가진 ClubGathering 이(가) 존재하지 않습니다."));
    }

    public List<ClubGathering> findByManagerId(Long managerId) {
        return repository.findByManagerIdOrderByTimeStampDesc(managerId);
    }

    public List<ClubGathering> findTrendGathering(String city) {
        return repository.findTrendGathering(city);
    }

    public List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId) {
        return repository.findParticipateInGatheringByApplierId(applierId);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
