package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.like.dto.LikeClubGatheringDto;
import com.junghun.common.domain.like.service.LikeClubGatheringService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
@Slf4j
class ClubGatheringServiceTest {

    @Autowired
    ClubGatheringService service;

    @Autowired
    UserService userService;

    @Autowired
    ClubGatheringApplyStatusService applyStatusService;

    @Autowired
    LikeClubGatheringService likeService;

    User manager;

    @BeforeEach
    void setClubGathering() {
        RegisterDto registerDto = new RegisterDto();
        Map<String, String> location = new HashMap<>();

        List<String> categoryList = new ArrayList<>();
        categoryList.add("sports");
        categoryList.add("language");
        categoryList.add("game");

        location.put("city", "세종");
        location.put("middlePlace", "한솔동");
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

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();

        tagList.add("hello");
        tagList.add("첫마을클럽");

        cityList.add("세종");

        ClubGatheringUploadDto clubGatheringUploadDto = new ClubGatheringUploadDto();
        clubGatheringUploadDto.setManagerId(manager.getId());
        clubGatheringUploadDto.setCategory("coffee");
        clubGatheringUploadDto.setDetailCategory("카페");
        clubGatheringUploadDto.setTitle("1번째모임");
        clubGatheringUploadDto.setContent("하하호호내용");
        clubGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        clubGatheringUploadDto.setImageList(imageList);
        clubGatheringUploadDto.setRecruitWay("Approval");
        clubGatheringUploadDto.setRecruitQuestion("");
        clubGatheringUploadDto.setCapacity(4);
        clubGatheringUploadDto.setTagList(tagList);
        clubGatheringUploadDto.setCityList(cityList);

        service.upload(clubGatheringUploadDto);
    }

    @AfterEach
    void clearClubGathering() {
        List<ClubGathering> clubGatheringList = service.findByManagerId(manager.getId());

        for (ClubGathering clubGathering : clubGatheringList) {
            service.deleteById(clubGathering.getId());
        }

        userService.deleteById(manager.getId());
    }

    @Test
    @DisplayName("소모임 생성")
    void upload() {
        List<ClubGathering> clubGatheringList = service.findByManagerId(manager.getId());
        Assertions.assertThat(clubGatheringList.size()).isEqualTo(1);
        Assertions.assertThat(clubGatheringList.get(0).getTitle()).isEqualTo("1번째모임");
    }

    @Test
    @DisplayName("인기 소모임 조회")
    void findTrendGathering() {

        //when
        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();

        cityList.add("대전");
        cityList.add("세종");

        ClubGatheringUploadDto clubGatheringUploadDto2 = new ClubGatheringUploadDto();
        clubGatheringUploadDto2.setManagerId(manager.getId());
        clubGatheringUploadDto2.setCategory("coffee");
        clubGatheringUploadDto2.setDetailCategory("카페");
        clubGatheringUploadDto2.setTitle("2번째모임");
        clubGatheringUploadDto2.setContent("하하호호내용");
        clubGatheringUploadDto2.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        clubGatheringUploadDto2.setImageList(imageList);
        clubGatheringUploadDto2.setRecruitWay("Approval");
        clubGatheringUploadDto2.setRecruitQuestion("");
        clubGatheringUploadDto2.setCapacity(4);
        clubGatheringUploadDto2.setTagList(tagList);
        clubGatheringUploadDto2.setCityList(cityList);

        ClubGatheringUploadDto clubGatheringUploadDto3 = new ClubGatheringUploadDto();
        clubGatheringUploadDto3.setManagerId(manager.getId());
        clubGatheringUploadDto3.setCategory("coffee");
        clubGatheringUploadDto3.setDetailCategory("카페");
        clubGatheringUploadDto3.setTitle("3번째모임");
        clubGatheringUploadDto3.setContent("하하호호내용");
        clubGatheringUploadDto3.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        clubGatheringUploadDto3.setImageList(imageList);
        clubGatheringUploadDto3.setRecruitWay("Approval");
        clubGatheringUploadDto3.setRecruitQuestion("");
        clubGatheringUploadDto3.setCapacity(4);
        clubGatheringUploadDto3.setTagList(tagList);
        clubGatheringUploadDto3.setCityList(cityList);

        ClubGathering secondClubGathering = service.upload(clubGatheringUploadDto2);
        ClubGathering thirdClubGathering = service.upload(clubGatheringUploadDto3);

        LikeClubGatheringDto secondLikeClubGatheringDto = new LikeClubGatheringDto();
        secondLikeClubGatheringDto.setUserId(manager.getId());
        secondLikeClubGatheringDto.setGatheringId(secondClubGathering.getId());

        LikeClubGatheringDto thirdLikeClubGatheringDto = new LikeClubGatheringDto();
        thirdLikeClubGatheringDto.setUserId(manager.getId());
        thirdLikeClubGatheringDto.setGatheringId(thirdClubGathering.getId());

        List<ClubGathering> clubGatheringList = service.findTrendGathering("대전");

        Assertions.assertThat(clubGatheringList.get(0).getTitle()).isEqualTo("2번째모임");
        Assertions.assertThat(clubGatheringList.get(1).getTitle()).isEqualTo("3번째모임");
        Assertions.assertThat(clubGatheringList.size()).isEqualTo(2);
    }
}