package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringPlaceDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.dto.UserPlaceDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserPlaceService;
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
import java.util.List;

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
    @Autowired
    UserPlaceService userPlaceService;
    User manager;
    User applier;

    @BeforeEach
    void setOneDayGathering() {

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
                .interestCategory(managerInterestCategory)
                .profileImage("image")
                .information("information")
                .build();

        UserPlaceDto managerPlaceDto = new UserPlaceDto();

        managerPlaceDto.setCity("세종");
        managerPlaceDto.setMiddlePlace("한솔동");
        managerPlaceDto.setDetailPlace("전체");

        manager =  userService.register(managerRegisterDto);

        userPlaceService.upload(manager.getId(),managerPlaceDto);

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
                .interestCategory(applierInterestCategory)
                .profileImage("image")
                .information("information")
                .build();

        UserPlaceDto applierPlaceDto = new UserPlaceDto();

        applierPlaceDto.setCity("세종");
        applierPlaceDto.setMiddlePlace("한솔동");
        applierPlaceDto.setDetailPlace("전체");

        applier =  userService.register(applierRegisterDto);
        userPlaceService.upload(applier.getId(),applierPlaceDto);

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
    @DisplayName("하루모임 수정하기")
    void updateGathering() {
        List<OneDayGathering> oneDayGatheringList = service.findByManagerId(manager.getId());

        OneDayGatheringUpdateDto oneDayGatheringUpdateDto = new OneDayGatheringUpdateDto();

        List<String> tagList = new ArrayList<>();
        tagList.add("배드민턴");
        tagList.add("운동");

        oneDayGatheringUpdateDto.setCategory("sports");
        oneDayGatheringUpdateDto.setDetailCategory("배드민턴");
        oneDayGatheringUpdateDto.setTitle("내일 배드민턴칠사람");
        oneDayGatheringUpdateDto.setContent("배드민턴 땡기는데 같이 목요일 저녁에 칠사람 있나요?");
        oneDayGatheringUpdateDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUpdateDto.setImageList(oneDayGatheringList.get(0).getImageList());
        oneDayGatheringUpdateDto.setRecruitWay("firstCome");
        oneDayGatheringUpdateDto.setRecruitQuestion("");
        oneDayGatheringUpdateDto.setCapacity(4);
        oneDayGatheringUpdateDto.setTagList(tagList);
        oneDayGatheringUpdateDto.setType("oneDay");
        oneDayGatheringUpdateDto.setOpeningDate(LocalDateTime.of(2024,4,21,15,0));
        oneDayGatheringUpdateDto.setHaveEntryFee(false);
        oneDayGatheringUpdateDto.setEntryFee(0);
        oneDayGatheringUpdateDto.setClubGatheringId(null);
        oneDayGatheringUpdateDto.setShowAllThePeople(true);

        service.update(oneDayGatheringList.get(0).getId(),oneDayGatheringUpdateDto);

        OneDayGathering gathering = service.findById(oneDayGatheringList.get(0).getId());

        Assertions.assertThat(gathering.getTagList()).containsExactly("배드민턴","운동");
        Assertions.assertThat(gathering.getTitle()).isEqualTo("내일 배드민턴칠사람");
    }

    @Test
    @DisplayName("하루모임 장소 수정하기")
    void updateGatheringPlace() {
        List<OneDayGathering> oneDayGatheringList = service.findByManagerId(manager.getId());
        OneDayGathering gathering = service.findById(oneDayGatheringList.get(0).getId());
        Assertions.assertThat(gathering.getPlace().getCity()).isEqualTo("경남");

        OneDayGatheringPlaceDto oneDayGatheringPlaceDto = new OneDayGatheringPlaceDto();
        oneDayGatheringPlaceDto.setCity("서울");
        oneDayGatheringPlaceDto.setMiddlePlace("동작구");
        oneDayGatheringPlaceDto.setDetailPlace("아디오스");

        placeService.update(gathering.getId(),oneDayGatheringPlaceDto);

        OneDayGathering updatedAfterGathering = service.findById(oneDayGatheringList.get(0).getId());

        Assertions.assertThat(updatedAfterGathering.getPlace().getCity()).isEqualTo("서울");
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

    @Test
    @DisplayName("오늘 시작하는 하루모임 조회하기")
    void findCanParticipateInTodayGathering(){
        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        tagList.add("카페");
        tagList.add("2030");

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
        List<OneDayGathering> gatheringSejongList = service.findByCity("세종");
        List<OneDayGathering> gatheringGyeongNamList = service.findByCity("경남");

        Assertions.assertThat(gatheringSejongList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringGyeongNamList.size()).isEqualTo(1);
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