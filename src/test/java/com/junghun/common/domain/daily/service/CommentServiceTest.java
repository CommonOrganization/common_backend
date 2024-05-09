package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.exception.NotFoundCommentsException;
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
class CommentServiceTest {

    @Autowired
    CommentService service;

    @Autowired
    DailyService dailyService;

    @Autowired
    UserService userService;
    Daily daily;
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

        user =  userService.register(registerDto);

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

        daily = dailyService.upload(dailyUploadDto);
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

    @Test
    @DisplayName("댓글 등록하기 테스트")
    void uploadComment(){
        CommentUploadDto commentUploadDto = new CommentUploadDto();
        commentUploadDto.setWriterId(user.getId());
        commentUploadDto.setDailyId(daily.getId());
        commentUploadDto.setContent("여기는 댓글입니다.");

        Comment uploadComment = service.upload(commentUploadDto);

        Comment comment = service.findById(uploadComment.getId());

        Assertions.assertThat(comment.getContent()).isEqualTo(uploadComment.getContent());
        Assertions.assertThat(comment.getWriter().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("댓글 수정하기 테스트")
    void updateComment(){
        CommentUploadDto commentUploadDto = new CommentUploadDto();
        commentUploadDto.setWriterId(user.getId());
        commentUploadDto.setDailyId(daily.getId());
        commentUploadDto.setContent("여기는 댓글입니다.");

        Comment uploadComment = service.upload(commentUploadDto);

        CommentUpdateDto commentUpdateDto = new CommentUpdateDto();
        commentUpdateDto.setContent("이건 새로운 댓글입니다.");

        Comment updateComment = service.update(uploadComment.getId(),commentUpdateDto);

        Assertions.assertThat(uploadComment.getContent()).isNotEqualTo(updateComment.getContent());
        Assertions.assertThat(uploadComment.getWriter().getId()).isEqualTo(updateComment.getId());
    }

    @Test
    @DisplayName("댓글 삭제하기 테스트")
    void deleteComment(){
        CommentUploadDto commentUploadDto = new CommentUploadDto();
        commentUploadDto.setWriterId(user.getId());
        commentUploadDto.setDailyId(daily.getId());
        commentUploadDto.setContent("여기는 댓글입니다.");

        Comment uploadComment = service.upload(commentUploadDto);

        service.deleteById(uploadComment.getId());

        Assertions.assertThatThrownBy(()->service.findById(uploadComment.getId())).isInstanceOf(NotFoundCommentsException.class);
    }

}