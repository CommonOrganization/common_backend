package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringImageUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringTagUploadDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.OneDayGatheringRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
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

    private final OneDayGatheringImageService imageService;
    private final OneDayGatheringTagService tagService;

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
                .haveEntryFee(oneDayGatheringUploadDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUploadDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUploadDto.isShowAllThePeople())
                .clubGathering(clubGathering)
                .build();



        OneDayGathering savedOneDayGathering = repository.save(gathering);

        OneDayGatheringImageUploadDto oneDayGatheringImageUploadDto = new OneDayGatheringImageUploadDto();
        oneDayGatheringImageUploadDto.setOneDayGathering(savedOneDayGathering);
        oneDayGatheringImageUploadDto.setImageList(oneDayGatheringUploadDto.getImageList());

        OneDayGatheringTagUploadDto oneDayGatheringTagUploadDto = new OneDayGatheringTagUploadDto();
        oneDayGatheringTagUploadDto.setOneDayGathering(savedOneDayGathering);
        oneDayGatheringTagUploadDto.setTagList(oneDayGatheringUploadDto.getTagList());

        imageService.upload(oneDayGatheringImageUploadDto);
        tagService.upload(oneDayGatheringTagUploadDto);

        return savedOneDayGathering;
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
                .recruitWay(oneDayGatheringUpdateDto.getRecruitWay())
                .recruitQuestion(oneDayGatheringUpdateDto.getRecruitQuestion())
                .capacity(oneDayGatheringUpdateDto.getCapacity())
                .timeStamp(writeDate)
                .type(oneDayGatheringUpdateDto.getType())
                .openingDate(oneDayGatheringUpdateDto.getOpeningDate())
                .haveEntryFee(oneDayGatheringUpdateDto.isHaveEntryFee())
                .entryFee(oneDayGatheringUpdateDto.getEntryFee())
                .showAllThePeople(oneDayGatheringUpdateDto.isShowAllThePeople())
                .clubGathering(clubGathering)
                .build();

        OneDayGatheringImageUploadDto oneDayGatheringImageUploadDto = new OneDayGatheringImageUploadDto();
        oneDayGatheringImageUploadDto.setOneDayGathering(updateGathering);
        oneDayGatheringImageUploadDto.setImageList(oneDayGatheringUpdateDto.getImageList());

        OneDayGatheringTagUploadDto oneDayGatheringTagUploadDto = new OneDayGatheringTagUploadDto();
        oneDayGatheringTagUploadDto.setOneDayGathering(updateGathering);
        oneDayGatheringTagUploadDto.setTagList(oneDayGatheringUpdateDto.getTagList());

        imageService.deleteAll(id);
        imageService.upload(oneDayGatheringImageUploadDto);

        tagService.deleteAll(id);
        tagService.upload(oneDayGatheringTagUploadDto);

        return repository.save(updateGathering);


    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
