package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class ReplyServiceTest {

    @Autowired
    ReplyService service;

    @Autowired
    DailyService dailyService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;
    Long dailyId;
    User user;

    @BeforeEach
    void setComment() {

        Map<String, Object> userPlace = new HashMap<>();
        userPlace.put("city", "세종");
        userPlace.put("county", "한솔동");
        userPlace.put("dong", "전체");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");


        RegisterDto registerDto = RegisterDto.builder()
                .email("test@naver.com")
                .name("test")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999, 1, 16))
                .userPlace(userPlace)
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        user = userService.register(registerDto);

        DailyUploadDto dailyUploadDto = new DailyUploadDto();

        List<String> imageList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();

        tagList.add("배드민턴");
        tagList.add("첫마을클럽");

        dailyUploadDto.setWriterId(user.getId());
        dailyUploadDto.setCategory("sports");
        dailyUploadDto.setDailyType("gathering");
        dailyUploadDto.setClubGatheringId(null);
        dailyUploadDto.setMainImage("image");
        dailyUploadDto.setImageList(imageList);
        dailyUploadDto.setContent("여기는 데일리 설명이 들어갑니다 / 1번 데일리:)");
        dailyUploadDto.setTagList(tagList);

        dailyId = dailyService.upload(dailyUploadDto).getId();
    }

    @AfterEach
    void clearDaily() {
        List<Daily> dailyList = dailyService.findAll();

        for (Daily daily : dailyList) {
            dailyService.deleteById(daily.getId());
        }

        User user = userService.findByEmail("test@naver.com");
        userService.deleteById(user.getId());
    }

}