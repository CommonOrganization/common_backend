package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.service.DailyService;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class OneDayGatheringServiceTest {

    @Autowired
    OneDayGatheringService service;

    @Autowired
    ClubGatheringService clubGatheringService;

    @Autowired
    OneDayGatheringApplyStatusService oneDayGatheringApplyStatusService;

    @Autowired
    UserService userService;
    User manager;
    User applier;

    @BeforeEach
    void setOneDayGathering() {
        Map<String, Object> managerPlace = new HashMap<>();
        managerPlace.put("city", "세종");
        managerPlace.put("county", "한솔동");
        managerPlace.put("dong", "전체");

        List<String> managerInterestCategory = new ArrayList<>();
        managerInterestCategory.add("sports");
        managerInterestCategory.add("language");
        managerInterestCategory.add("game");


        RegisterDto managerRegisterDto = RegisterDto.builder()
                .email("manager@naver.com")
                .name("manager")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999, 1, 16))
                .userPlace(managerPlace)
                .interestCategory(managerInterestCategory)
                .profileImage("image")
                .information("information")
                .build();

        Map<String, Object> applierPlace = new HashMap<>();
        applierPlace.put("city", "세종");
        applierPlace.put("county", "한솔동");
        applierPlace.put("dong", "전체");

        List<String> applierInterestCategory = new ArrayList<>();
        applierInterestCategory.add("sports");
        applierInterestCategory.add("language");
        applierInterestCategory.add("game");


        RegisterDto applierRegisterDto = RegisterDto.builder()
                .email("applier@naver.com")
                .name("applier")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999, 1, 16))
                .userPlace(applierPlace)
                .interestCategory(applierInterestCategory)
                .profileImage("image")
                .information("information")
                .build();

        manager =  userService.register(managerRegisterDto);
        applier =  userService.register(applierRegisterDto);

        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        tagList.add("카페");
        tagList.add("2030");
        tagList.add("주변 놀거리");
        Map<String,Object> place = new HashMap<>();
        place.put("city","경남");
        place.put("county","창원시");
        place.put("detail","카페 소담아");
        place.put("dong","전체");

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("coffee");
        oneDayGatheringUploadDto.setDetailCategory("카페");
        oneDayGatheringUploadDto.setTitle("금요일에 마산가는데 카페 갈사람?!");
        oneDayGatheringUploadDto.setContent("금요일에 일있어서 마산가는데 오후에 시간이 비어요ㅠㅠ같이 카페나 어디 놀러갈사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("firstCome");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(4);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.of(2024,4,21,15,0));
        oneDayGatheringUploadDto.setPlace(place);
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        service.upload(oneDayGatheringUploadDto);
    }

    @AfterEach
    void clearOneDayGathering() {
        List<OneDayGathering> oneDayGatheringList = service.findByManagerId(manager.getId());

        for(OneDayGathering oneDayGathering : oneDayGatheringList){
            service.deleteById(oneDayGathering.getId());
        }

        userService.deleteById(manager.getId());
        userService.deleteById(applier.getId());
    }

    @Test
    @DisplayName("매니저 ID로 검색하기")
    void findByManagerId() {
        List<OneDayGathering> oneDayGatheringList = service.findByManagerId(manager.getId());

        Assertions.assertThat(oneDayGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("지원자 ID로 검색하기")
    void findByApplierId() {

        List<OneDayGathering> gatheringList = service.findByManagerId(manager.getId());

        oneDayGatheringApplyStatusService.applyGathering(applier.getId(),gatheringList.get(0).getId());

        List<OneDayGathering> oneDayGatheringList = service.findByApplierId(applier.getId());

        Assertions.assertThat(oneDayGatheringList.size()).isEqualTo(1);
    }
}