package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.OneDayGatheringUpdateDto;
import com.junghun.common.domain.gathering.dto.OneDayGatheringUploadDto;
import com.junghun.common.domain.gathering.model.GatheringApplyStatus;
import com.junghun.common.domain.gathering.model.GatheringType;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    GatheringApplyStatusService gatheringApplyStatusService;

    @Autowired
    UserService userService;
    User manager;
    User applier;

    @BeforeEach
    void setOneDayGathering() {
        RegisterDto registerDto = new RegisterDto();
        Map<String, String> location = new HashMap<>();

        List<String> categoryList = new ArrayList<>();
        categoryList.add("sports");
        categoryList.add("language");
        categoryList.add("game");

        location.put("city", "대전");
        location.put("middlePlace", "유성구");
        location.put("detailPlace", "전체");

        registerDto.setEmail("register@naver.com");
        registerDto.setName("가입자");
        registerDto.setPassword("password");
        registerDto.setGender("남성");
        registerDto.setBirthday(LocalDate.of(1999, 1, 16));
        registerDto.setCategoryList(categoryList);
        registerDto.setProfileImage("image");
        registerDto.setInformation("information");
        registerDto.setLocation(location);

        manager = userService.register(registerDto);

        RegisterDto applierRegisterDto = new RegisterDto();
        Map<String, String> applierLocation = new HashMap<>();

        List<String> applierCategoryList = new ArrayList<>();
        applierCategoryList.add("sports");
        applierCategoryList.add("language");
        applierCategoryList.add("game");

        applierLocation.put("city", "대전");
        applierLocation.put("middlePlace", "유성구");
        applierLocation.put("detailPlace", "전체");

        applierRegisterDto.setEmail("applier@naver.com");
        applierRegisterDto.setName("applier");
        applierRegisterDto.setPassword("password");
        applierRegisterDto.setGender("남성");
        applierRegisterDto.setBirthday(LocalDate.of(1999, 1, 16));
        applierRegisterDto.setCategoryList(applierCategoryList);
        applierRegisterDto.setProfileImage("image");
        applierRegisterDto.setInformation("information");
        applierRegisterDto.setLocation(applierLocation);

        applier = userService.register(applierRegisterDto);

        OneDayGatheringUploadDto oneDayGatheringUploadDto = new OneDayGatheringUploadDto();

        List<String> imageList = new ArrayList<>();

        List<String> tagList = new ArrayList<>();
        tagList.add("카페");
        tagList.add("2030");
        tagList.add("주변 놀거리");

        Map<String, String> gatheringLocation = new HashMap<>();
        gatheringLocation.put("city", "대전");
        gatheringLocation.put("middlePlace", "유성구");
        gatheringLocation.put("detailPlace", "카페 소담아");

        oneDayGatheringUploadDto.setManagerId(manager.getId());
        oneDayGatheringUploadDto.setCategory("coffee");
        oneDayGatheringUploadDto.setDetailCategory("카페");
        oneDayGatheringUploadDto.setTitle("금요일에 마산가는데 카페 갈사람?!");
        oneDayGatheringUploadDto.setContent("금요일에 일있어서 마산가는데 오후에 시간이 비어요ㅠㅠ같이 카페나 어디 놀러갈사람 있을까요?!");
        oneDayGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUploadDto.setImageList(imageList);
        oneDayGatheringUploadDto.setRecruitWay("Approval");
        oneDayGatheringUploadDto.setRecruitQuestion("");
        oneDayGatheringUploadDto.setCapacity(10);
        oneDayGatheringUploadDto.setTagList(tagList);
        oneDayGatheringUploadDto.setType("oneDay");
        oneDayGatheringUploadDto.setOpeningDate(LocalDateTime.of(2024, 4, 21, 15, 0));
        oneDayGatheringUploadDto.setHaveEntryFee(false);
        oneDayGatheringUploadDto.setEntryFee(0);
        oneDayGatheringUploadDto.setLocation(gatheringLocation);
        oneDayGatheringUploadDto.setClubGatheringId(null);
        oneDayGatheringUploadDto.setShowAllThePeople(true);

        service.upload(oneDayGatheringUploadDto);
    }

    @AfterEach
    void clearOneDayGathering() {
        List<OneDayGathering> oneDayGatheringList = service.findByManagerId(manager.getId());

        for (OneDayGathering oneDayGathering : oneDayGatheringList) {
            service.deleteById(oneDayGathering.getId());
        }

        gatheringApplyStatusService.deleteAll();

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

        Map<String, String> location = new HashMap<>();
        location.put("city", "서울");
        location.put("middlePlace", "동작구");
        location.put("detailPlace", "아디오스");

        oneDayGatheringUpdateDto.setCategory("sports");
        oneDayGatheringUpdateDto.setDetailCategory("배드민턴");
        oneDayGatheringUpdateDto.setTitle("내일 배드민턴칠사람");
        oneDayGatheringUpdateDto.setContent("배드민턴 땡기는데 같이 목요일 저녁에 칠사람 있나요?");
        oneDayGatheringUpdateDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        oneDayGatheringUpdateDto.setImageList(oneDayGatheringList.get(0).getImageList());
        oneDayGatheringUpdateDto.setRecruitWay("FirstCome");
        oneDayGatheringUpdateDto.setRecruitQuestion("");
        oneDayGatheringUpdateDto.setCapacity(4);
        oneDayGatheringUpdateDto.setTagList(tagList);
        oneDayGatheringUpdateDto.setType("oneDay");
        oneDayGatheringUpdateDto.setOpeningDate(LocalDateTime.of(2024, 4, 21, 15, 0));
        oneDayGatheringUpdateDto.setHaveEntryFee(false);
        oneDayGatheringUpdateDto.setLocation(location);
        oneDayGatheringUpdateDto.setEntryFee(0);
        oneDayGatheringUpdateDto.setClubGatheringId(null);
        oneDayGatheringUpdateDto.setShowAllThePeople(true);

        service.update(oneDayGatheringList.get(0).getId(), oneDayGatheringUpdateDto);

        OneDayGathering gathering = service.findById(oneDayGatheringList.get(0).getId());

        Assertions.assertThat(gathering.getTitle()).isEqualTo("내일 배드민턴칠사람");
        Assertions.assertThat(gathering.getTagList()).containsAll(tagList);
        Assertions.assertThat(gathering.getLocation().get("city")).isEqualTo("서울");
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

        gatheringApplyStatusService.applyGathering(applier.getId(), gatheringList.get(0).getId(), GatheringType.OneDayGathering);

        List<OneDayGathering> oneDayGatheringList = service.findByApplierId(applier.getId());

        Assertions.assertThat(oneDayGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("지원자 승인 후 조회하기")
    @Transactional
    void findParticipateInGatheringByApplierId() {

        List<OneDayGathering> gatheringList = service.findByManagerId(manager.getId());
        GatheringApplyStatus gatheringApplyStatus = gatheringApplyStatusService.applyGathering(applier.getId(), gatheringList.get(0).getId(), GatheringType.OneDayGathering);

        gatheringApplyStatusService.approveGathering(gatheringApplyStatus.getId());
        List<OneDayGathering> afterApprovedOneDayGatheringList = service.findParticipateInGatheringByApplierId(applier.getId());

        Assertions.assertThat(afterApprovedOneDayGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("오늘 시작하는 하루모임 조회하기")
    void findCanParticipateInTodayGathering() {
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
        oneDayGatheringUploadDto.setRecruitWay("Approval");
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

        List<OneDayGathering> todayGatheringList = service.findTodayGathering();
        Assertions.assertThat(todayGatheringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("카테고리 배열로 조회하기")
    void findByCategoryIn() {

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
        oneDayGatheringUploadDto.setRecruitWay("FirstCome");
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
    void findByCity() {
        List<OneDayGathering> gatheringSejongList = service.findByCity("세종");
        List<OneDayGathering> gatheringGyeongNamList = service.findByCity("대전");

        Assertions.assertThat(gatheringSejongList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringGyeongNamList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("카테고리로 조회하기")
    void findByCategory() {
        List<OneDayGathering> gatheringCoffeeList = service.findByCategory("coffee");
        List<OneDayGathering> gatheringSportsList = service.findByCategory("sports");

        Assertions.assertThat(gatheringSportsList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringCoffeeList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("키워드로 조회하기")
    void findByKeyword() {
        List<OneDayGathering> gatheringCoffeeList = service.findByKeyword("카페");
        List<OneDayGathering> gatheringSojuList = service.findByKeyword("소주");

        Assertions.assertThat(gatheringSojuList.size()).isEqualTo(0);
        Assertions.assertThat(gatheringCoffeeList.size()).isEqualTo(1);
    }
}