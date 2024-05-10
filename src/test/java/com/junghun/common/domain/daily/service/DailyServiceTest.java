package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Slf4j
class DailyServiceTest {
    @Autowired
    DailyService service;

    @Autowired
    UserService userService;

    User user;

    @BeforeEach
    void setDaily() {

        List<String> categoryList = new ArrayList<>();
        categoryList.add("sports");
        categoryList.add("language");
        categoryList.add("game");


        RegisterDto registerDto = RegisterDto.builder()
                .email("test@naver.com")
                .name("test")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999, 1, 16))
                .categoryList(categoryList)
                .profileImage("image")
                .information("information")
                .build();

        user = userService.register(registerDto);

        DailyUploadDto dailyUploadDto1 = new DailyUploadDto();

        List<String> imageList1 = new ArrayList<>();
        List<String> tagList1 = new ArrayList<>();

        tagList1.add("배드민턴");
        tagList1.add("첫마을클럽");

        dailyUploadDto1.setWriterId(user.getId());
        dailyUploadDto1.setCategory("sports");
        dailyUploadDto1.setDailyType("gathering");
        dailyUploadDto1.setClubGatheringId(null);
        dailyUploadDto1.setMainImage("image");
        dailyUploadDto1.setImageList(imageList1);
        dailyUploadDto1.setContent("여기는 데일리 설명이 들어갑니다 / 1번 데일리:)");
        dailyUploadDto1.setTagList(tagList1);

        DailyUploadDto dailyUploadDto2 = new DailyUploadDto();

        List<String> imageList2 = new ArrayList<>();
        List<String> tagList2 = new ArrayList<>();

        tagList2.add("카공");
        tagList2.add("공부");

        dailyUploadDto2.setWriterId(user.getId());
        dailyUploadDto2.setCategory("study");
        dailyUploadDto2.setDailyType("gathering");
        dailyUploadDto2.setClubGatheringId(null);
        dailyUploadDto2.setMainImage("image");
        dailyUploadDto2.setImageList(imageList2);
        dailyUploadDto2.setContent("여기는 데일리 설명이 들어갑니다 / 2번 데일리:)");
        dailyUploadDto2.setTagList(tagList2);

        DailyUploadDto dailyUploadDto3 = new DailyUploadDto();

        List<String> imageList3 = new ArrayList<>();
        List<String> tagList3 = new ArrayList<>();

        tagList3.add("카페");
        tagList3.add("수다");
        tagList3.add("세종맘");

        dailyUploadDto3.setWriterId(user.getId());
        dailyUploadDto3.setCategory("coffee");
        dailyUploadDto3.setDailyType("own");
        dailyUploadDto3.setClubGatheringId(null);
        dailyUploadDto3.setMainImage("image");
        dailyUploadDto3.setImageList(imageList3);
        dailyUploadDto3.setContent("여기는 데일리 설명이 들어갑니다 / 3번 데일리:)");
        dailyUploadDto3.setTagList(tagList3);

        service.upload(dailyUploadDto1);
        service.upload(dailyUploadDto2);
        service.upload(dailyUploadDto3);
    }

    @AfterEach
    void clearDaily() {
        List<Daily> dailyList = service.findAll();

        for (Daily daily : dailyList) {
            service.deleteById(daily.getId());
        }

        User user = userService.findByEmail("test@naver.com");
        userService.deleteById(user.getId());
    }

    @Test
    @DisplayName("전부 불러오기")
    void findAll() {
        List<Daily> dailyList = service.findAll();

        Assertions.assertThat(dailyList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("작성자 ID로 작성자가 작성한 데일리 불러오기")
    void findByWriterId() {
        List<Daily> dailyList = service.findByWriterId(user.getId());

        Assertions.assertThat(dailyList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("키워드로 작성자가 작성한 데일리 불러오기")
    void findByKeyword() {
        List<Daily> dailyList = service.findByKeyword("데일리");

        Assertions.assertThat(dailyList.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("카테고리로 작성자가 작성한 데일리 불러오기")
    void findByCategory() {
        List<Daily> dailyList = service.findByCategory("sports");
        for (Daily daily : dailyList) {
            log.info("daily 내용 : {}", daily.getContent());
        }
        Assertions.assertThat(dailyList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("데일리 불러와서 수정하기")
    void findDailyAndUpdateTagList() {
        List<Daily> dailyList = service.findByWriterId(user.getId());

        Daily daily = dailyList.get(0);

        DailyUpdateDto dailyUpdateDto = new DailyUpdateDto();

        List<String> newTagList = new ArrayList<>();
        List<String> newImageList = new ArrayList<>();
        newTagList.add("하하호호");
        newTagList.add("이건 수정된태그");

        dailyUpdateDto.setCategory(daily.getCategory());
        dailyUpdateDto.setDetailCategory(daily.getDetailCategory());
        dailyUpdateDto.setDailyType(daily.getDailyType());
        dailyUpdateDto.setMainImage(daily.getMainImage());
        dailyUpdateDto.setImageList(newImageList);
        dailyUpdateDto.setContent(daily.getContent());
        dailyUpdateDto.setTagList(newTagList);

        service.update(daily.getId(), dailyUpdateDto);

        Daily findDaily = service.findById(daily.getId());

        Assertions.assertThat(findDaily.getTagList().size()).isEqualTo(2);
        Assertions.assertThat(findDaily.getContent()).isEqualTo(daily.getContent());
    }
}