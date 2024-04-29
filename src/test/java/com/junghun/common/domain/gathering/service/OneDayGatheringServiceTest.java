package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.service.DailyService;
import com.junghun.common.domain.gathering.dto.OneDayGatheringPlaceDto;
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
    OneDayGatheringPlaceService placeService;

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

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("coffee");
        oneDayGatheringUploadDto.setDetailCategory("카페");
        oneDayGatheringUploadDto.setTitle("금요일에 마산가는데 카페 갈사람?!");
        oneDayGatheringUploadDto.setContent("금요일에 일있어서 마산가는데 오후에 시간이 비어요ㅠㅠ같이 카페나 어디 놀러갈사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("approval");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(4);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.of(2024,4,21,15,0));
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        OneDayGathering gathering = service.upload(oneDayGatheringUploadDto);

        OneDayGatheringPlaceDto oneDayGatheringPlaceDto = new OneDayGatheringPlaceDto();
        oneDayGatheringPlaceDto.setCity("경남");
        oneDayGatheringPlaceDto.setMiddlePlace("창원시");
        oneDayGatheringPlaceDto.setDetailPlace("카페 소담아");

        placeService.upload(gathering.getId(),oneDayGatheringPlaceDto);
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

    @Test
    @DisplayName("지원자 승인 후 조회하기")
    void findParticipateInGatheringByApplierId() {

        List<OneDayGathering> gatheringList = service.findByManagerId(manager.getId());
        // 지원 후에
        oneDayGatheringApplyStatusService.applyGathering(applier.getId(),gatheringList.get(0).getId());

        List<OneDayGathering> preApprovedOneDayGatheringList = service.findParticipateInGatheringByApplierId(applier.getId());
        // 승인되기 이전에는 승인된 Gathering 존재 X
        Assertions.assertThat(preApprovedOneDayGatheringList.size()).isEqualTo(0);
        // 승인 후에
        oneDayGatheringApplyStatusService.approveGathering(applier.getId(),gatheringList.get(0).getId());

        List<OneDayGathering> afterApprovedOneDayGatheringList = service.findParticipateInGatheringByApplierId(applier.getId());
        // 승인된 Gathering 1개 존재
        Assertions.assertThat(afterApprovedOneDayGatheringList.size()).isEqualTo(1);
    }

    /**
     *      findWithToday()
     *       findWithSoon()
     *     findByCategoryIn(String[] categories)
     *     findByCity(String city)
     *     findByCategory(String category){
     *      findByKeyword(String keyword)
     */
    @Test
    @DisplayName("오늘 시작하는 하루모임 조회하기")
    void findCanParticipateInTodayGathering(){
        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        tagList.add("카페");
        tagList.add("2030");
        Map<String,Object> place = new HashMap<>();
        place.put("city","경남");
        place.put("detail","카페 소담아");
        place.put("dong","전체");

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("coffee");
        oneDayGatheringUploadDto.setDetailCategory("카페");
        oneDayGatheringUploadDto.setTitle("카페 갈사람?!");
        oneDayGatheringUploadDto.setContent("오후에 시간이 비어요ㅠㅠ같이 카페나 어디 놀러갈사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("approval");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(4);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.now().plusHours(1));
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        service.upload(oneDayGatheringUploadDto);

        List<OneDayGathering> todayGatheringList = service.findWithToday();
        Assertions.assertThat(todayGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("곧 시작하는 하루모임 조회하기")
    void findCanParticipateInSoonGathering(){
        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        tagList.add("카페");
        tagList.add("2030");
        Map<String,Object> place = new HashMap<>();
        place.put("city","경남");
        place.put("detail","카페 소담아");
        place.put("dong","전체");

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("coffee");
        oneDayGatheringUploadDto.setDetailCategory("카페");
        oneDayGatheringUploadDto.setTitle("카페 갈사람?!");
        oneDayGatheringUploadDto.setContent("오후에 시간이 비어요ㅠㅠ같이 카페나 어디 놀러갈사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("approval");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(4);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.now().plusDays(3));
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        service.upload(oneDayGatheringUploadDto);
        service.upload(oneDayGatheringUploadDto);
        service.upload(oneDayGatheringUploadDto);

        List<OneDayGathering> todayGatheringList = service.findWithSoon();
        Assertions.assertThat(todayGatheringList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("카테고리 배열로 조회하기")
    void findByCategoryIn(){

        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        tagList.add("배그");
        Map<String,Object> place = new HashMap<>();

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("game");
        oneDayGatheringUploadDto.setDetailCategory("배그");
        oneDayGatheringUploadDto.setTitle("배그 할사람?!");
        oneDayGatheringUploadDto.setContent("오후에 시간이 비어요ㅠㅠ같이 배그나 할사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("firstcome");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(4);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.now().plusDays(3));
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        service.upload(oneDayGatheringUploadDto);

        String[] strings = new String[2];
        strings[0] = "hello";
        List<OneDayGathering> preGatheringList = service.findByCategoryIn(strings);
        Assertions.assertThat(preGatheringList.size()).isEqualTo(0);

        strings[1] = "game";
        List<OneDayGathering> afterGatheringList = service.findByCategoryIn(strings);
        Assertions.assertThat(afterGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("도시로 조회하기")
    void findByCity(){
        // 수정예정
        //List<OneDayGathering> gatheringSejongList = service.findByCity("세종");
//        List<OneDayGathering> gatheringGyeongNamList = service.findByCity("경남");
//
//        //Assertions.assertThat(gatheringSejongList.size()).isEqualTo(0);
//        Assertions.assertThat(gatheringGyeongNamList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("카테고리로 조회하기")
    void findByCategory(){
        List<OneDayGathering> gatheringCoffeeList = service.findByCategory("coffee");
        List<OneDayGathering> gatheringSportsList = service.findByCategory("sports");

        Assertions.assertThat(gatheringSportsList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringCoffeeList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("키워드로 조회하기")
    void findByKeyword(){
        List<OneDayGathering> gatheringCoffeeList = service.findByKeyword("카페");
        List<OneDayGathering> gatheringSojuList = service.findByKeyword("소주");

        Assertions.assertThat(gatheringSojuList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringCoffeeList.size()).isEqualTo(1);
    }
}