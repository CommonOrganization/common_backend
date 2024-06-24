package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.gathering.dto.*;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.model.RecruitWay;
import com.junghun.common.domain.gathering.repository.ClubGatheringApplyStatusRepository;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import com.junghun.common.domain.user.service.UserService;
import com.junghun.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringService {
    private final ClubGatheringRepository repository;
    private final UserRepository userRepository;
    private final DailyRepository dailyRepository;
    private final ClubGatheringApplyStatusRepository clubGatheringApplyStatusRepository;

    public ClubGathering upload(ClubGatheringUploadDto clubGatheringUploadDto) {
        User manager = userRepository.findById(clubGatheringUploadDto.getManagerId())
                .orElseThrow(()->new NotFoundUserException(clubGatheringUploadDto.getManagerId()+"를 가진 이용자가 존재하지 않습니다."));
        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering gathering = ClubGathering.builder()
                .manager(manager)
                .category(clubGatheringUploadDto.getCategory())
                .detailCategory(clubGatheringUploadDto.getDetailCategory())
                .title(clubGatheringUploadDto.getTitle())
                .content(clubGatheringUploadDto.getContent())
                .mainImage(clubGatheringUploadDto.getMainImage())
                .recruitWay(RecruitWay.valueOf(clubGatheringUploadDto.getRecruitWay()))
                .recruitQuestion(clubGatheringUploadDto.getRecruitQuestion())
                .capacity(clubGatheringUploadDto.getCapacity())
                .imageList(ConvertUtils.getStringByList(clubGatheringUploadDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(clubGatheringUploadDto.getTagList()))
                .cityList(ConvertUtils.getStringByList(clubGatheringUploadDto.getCityList()))
                .timeStamp(writeDate)
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
                .recruitWay(RecruitWay.valueOf(clubGatheringUpdateDto.getRecruitWay()))
                .recruitQuestion(clubGatheringUpdateDto.getRecruitQuestion())
                .capacity(clubGatheringUpdateDto.getCapacity())
                .imageList(ConvertUtils.getStringByList(clubGatheringUpdateDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(clubGatheringUpdateDto.getTagList()))
                .cityList(ConvertUtils.getStringByList(clubGatheringUpdateDto.getCityList()))
                .timeStamp(writeDate)
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

    public List<ClubGathering> findTrendGathering(String city) {
        return repository.findTrendGathering(city);
    }

    public List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId) {
        return repository.findParticipateInGatheringByApplierId(applierId);
    }

    public List<ClubGathering> findByKeyword(String city,String keyword) {
        return repository.findByKeyword(city,keyword);
    }

    public List<ClubGathering> findByCategory(String city,String category) {
        return repository.findByCategory(city,category);
    }

    public List<String> filterRankingCategories(String city,List<String> categoryList) {
        String categoryListString = ConvertUtils.getStringByList(categoryList);
        return repository.filterRankingCategories(city,categoryListString);
    }

    @Transactional
    public void deleteById(Long id) {
        dailyRepository.deleteDailiesByClubGathering(id);
        clubGatheringApplyStatusRepository.deleteApplyStatusByClubGathering(id);
        repository.deleteById(id);
    }
}
