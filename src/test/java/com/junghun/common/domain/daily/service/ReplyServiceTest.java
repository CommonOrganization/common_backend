package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.*;
import com.junghun.common.domain.daily.model.Comment;
import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.daily.model.Reply;
import com.junghun.common.domain.daily.exception.NotFoundReplyException;
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
    Comment comment;

    @BeforeEach
    void setComment() {


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

        CommentUploadDto commentUploadDto = new CommentUploadDto();
        commentUploadDto.setWriterId(user.getId());
        commentUploadDto.setDailyId(dailyId);
        commentUploadDto.setContent("여기는 댓글입니다.");

        comment = commentService.upload(commentUploadDto);

    }

    @AfterEach
    void clearDaily() {
        List<Daily> dailyList = dailyService.findAll();

        for (Daily daily : dailyList) {
            dailyService.deleteById(daily.getId());
        }

        userService.deleteById(user.getId());
    }

    @Test
    @DisplayName("대댓글 등록하기 테스트")
    void uploadComment() {
        ReplyUploadDto replyUploadDto = new ReplyUploadDto();
        replyUploadDto.setWriterId(user.getId());
        replyUploadDto.setCommentId(comment.getId());
        replyUploadDto.setContent("여기는 대댓글입니다.");

        Reply uploadReply = service.upload(replyUploadDto);

        Reply reply = service.findById(uploadReply.getId());

        Assertions.assertThat(reply.getContent()).isEqualTo("여기는 대댓글입니다.");
        Assertions.assertThat(reply.getWriter().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("대댓글 수정하기 테스트")
    void updateComment() {
        ReplyUploadDto replyUploadDto = new ReplyUploadDto();
        replyUploadDto.setWriterId(user.getId());
        replyUploadDto.setCommentId(comment.getId());
        replyUploadDto.setContent("여기는 대댓글입니다.");

        Reply uploadReply = service.upload(replyUploadDto);

        ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto();
        replyUpdateDto.setContent("이건 새로운 대댓글입니다.");

        Reply updateReply = service.update(uploadReply.getId(), replyUpdateDto);

        Assertions.assertThat(uploadReply.getContent()).isNotEqualTo(updateReply.getContent());
        Assertions.assertThat(uploadReply.getId()).isEqualTo(updateReply.getId());
    }

    @Test
    @DisplayName("대댓글 삭제하기 테스트")
    void deleteComment() {
        ReplyUploadDto replyUploadDto = new ReplyUploadDto();
        replyUploadDto.setWriterId(user.getId());
        replyUploadDto.setCommentId(comment.getId());
        replyUploadDto.setContent("여기는 대댓글입니다.");

        Reply uploadReply = service.upload(replyUploadDto);

        service.deleteById(uploadReply.getId());

        Assertions.assertThatThrownBy(() -> service.findById(uploadReply.getId())).isInstanceOf(NotFoundReplyException.class);
    }

}