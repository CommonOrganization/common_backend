package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.dto.ClubGatheringUploadDto;
import com.junghun.common.domain.gathering.entity.ClubGathering;
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
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
class ClubGatheringServiceTest {

    @Autowired
    ClubGatheringService service;

    @Autowired
    UserService userService;

    @Autowired
    ClubGatheringApplyStatusService applyStatusService;

    User manager;

    @BeforeEach
    void setClubGathering() {
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

        manager =  userService.register(managerRegisterDto);

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();

        tagList.add("hello");
        tagList.add("첫마을클럽");

        ClubGatheringUploadDto clubGatheringUploadDto = new ClubGatheringUploadDto();
        clubGatheringUploadDto.setManagerId(manager.getId());
        clubGatheringUploadDto.setCategory("coffee");
        clubGatheringUploadDto.setDetailCategory("카페");
        clubGatheringUploadDto.setTitle("제목");
        clubGatheringUploadDto.setContent("하하호호내용");
        clubGatheringUploadDto.setMainImage("https://firebasestorage.googleapis.com/v0/b/common-2fea2.appspot.com/o/gathering%2F1698587984940784?alt=media&token=39a0e6db-8baa-49cf-b0ee-cec47765a327");
        clubGatheringUploadDto.setImageList(imageList);
        clubGatheringUploadDto.setRecruitWay("approval");
        clubGatheringUploadDto.setRecruitQuestion("");
        clubGatheringUploadDto.setCapacity(4);
        clubGatheringUploadDto.setTagList(tagList);
        clubGatheringUploadDto.setCityList(cityList);

        service.upload(clubGatheringUploadDto);
    }

    @AfterEach
    void clearClubGathering() {
        List<ClubGathering> clubGatheringList = service.findByManagerId(manager.getId());

        for(ClubGathering clubGathering : clubGatheringList){
            service.deleteById(clubGathering.getId());
        }

        userService.deleteById(manager.getId());
    }

    @Test
    @DisplayName("소모임 생성")
    void upload() {
        List<ClubGathering> clubGatheringList = service.findByManagerId(manager.getId());

        Assertions.assertThat(clubGatheringList.get(0).getTagList()).containsExactly("hello","첫마을클럽");
    }

    @Test
    @DisplayName("인기 소모임 조회")
    void findTrendGathering() {
        List<ClubGathering> clubGatheringList = service.findTrendGathering();
        Assertions.assertThat(clubGatheringList.get(0).getTagList()).containsExactly("hello","첫마을클럽");
    }
}