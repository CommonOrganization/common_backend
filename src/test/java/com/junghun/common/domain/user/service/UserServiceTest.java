package com.junghun.common.domain.user.service;


import com.junghun.common.config.MainConfig;
import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.dto.UserPlaceDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.entity.UserPlace;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@Slf4j
@Import(MainConfig.class)
class UserServiceTest {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserService service;

    @Autowired
    UserPlaceService placeService;

    @BeforeEach
    void setUser() {

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
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        User user = service.register(registerDto);

        UserPlaceDto userPlace = new UserPlaceDto();
        userPlace.setCity("세종");
        userPlace.setMiddlePlace("한솔동");
        userPlace.setDetailPlace("전체");

        placeService.upload(user.getId(), userPlace);
    }

    @AfterEach
    void clearUser() {
        service.resetPassword("test@naver.com", "password");
        User user = service.login("test@naver.com", "password");
        service.deleteById(user.getId());
    }

    @Test
    @DisplayName("회원가입하고 로그인하기")
    void register() {
        // given
        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");

        RegisterDto registerDto = RegisterDto.builder()
                .email("register@naver.com")
                .name("가입자")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999, 1, 16))
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        // when
        User user = service.register(registerDto);

        UserPlaceDto userPlaceDto = new UserPlaceDto();

        userPlaceDto.setCity("세종");
        userPlaceDto.setMiddlePlace("한솔동");
        userPlaceDto.setDetailPlace("전체");

        placeService.upload(user.getId(), userPlaceDto);

        User loginUser = service.login("register@naver.com", "password");
        // then
        InformationDto userInformation = service.findInformationById(loginUser.getId());

        Assertions.assertThat(userInformation.getName()).isEqualTo("가입자");
    }

    @Test
    @DisplayName("정보 수정 테스트")
    void loginAndUpdateUser() {
        //given
        User loginUser = service.login("test@naver.com", "password");

        InformationDto informationDto = new InformationDto();
        informationDto.setName("가입자");
        informationDto.setInformation("이건 회원정보");
        informationDto.setProfileImage("helloImage");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("study");
        interestCategory.add("game");

        // when
        service.updatePassword(loginUser.getId(), "password2");
        service.updateInformation(loginUser.getId(), informationDto);
        service.updateInterestCategory(loginUser.getId(), interestCategory);

        UserPlaceDto userPlaceDto = new UserPlaceDto();

        userPlaceDto.setCity("대전");
        userPlaceDto.setMiddlePlace("유성구");
        userPlaceDto.setDetailPlace("궁동");

        placeService.update(loginUser.getId(), userPlaceDto);

        User updatedUser = service.login("test@naver.com", "password2");

        // then
        Assertions.assertThat(updatedUser.getName()).isEqualTo("가입자");
        Assertions.assertThat(updatedUser.getInformation()).isEqualTo("이건 회원정보");
        Assertions.assertThat(updatedUser.getUserPlace().getCity()).isEqualTo("대전");
        Assertions.assertThat(updatedUser.getInterestCategory()).containsExactly("study", "game");
    }

    @Test
    @DisplayName("비밀번호 초기화 테스트")
    void resetPassword() {

        service.resetPassword("test@naver.com", "password123");

        User loginFailedUser = service.login("test@naver.com", "password");

        Assertions.assertThat(loginFailedUser).isNull();

        User loginSuccessUser = service.login("test@naver.com", "password123");

        Assertions.assertThat(loginSuccessUser.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void duplicatedEmail() {

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
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        Assertions.assertThatThrownBy(() -> service.register(registerDto)).isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("관심 카테고리 수정 테스트")
    void updateInterestCategory() {

        User loginUser = service.login("test@naver.com", "password");

        List<String> newInterestCategory = new ArrayList<>();
        newInterestCategory.add("sports");
        newInterestCategory.add("language");
        newInterestCategory.add("game");
        newInterestCategory.add("coffee");
        newInterestCategory.add("music");

        service.updateInterestCategory(loginUser.getId(), newInterestCategory);

        User updatedUser = service.login("test@naver.com", "password");

        Assertions.assertThat(updatedUser.getInterestCategory()).containsExactly("sports", "language", "game", "coffee", "music");

        Assertions.assertThat(updatedUser.getInterestCategory().size()).isEqualTo(5);

    }

    @Test
    @DisplayName("유저 지역 수정 테스트")
    void updateUserPlace() {

        User loginUser = service.login("test@naver.com", "password");

        UserPlaceDto userPlaceDto = new UserPlaceDto();
        userPlaceDto.setCity("서울");
        userPlaceDto.setMiddlePlace("동작구");
        userPlaceDto.setDetailPlace("왕천파닭");

        placeService.update(loginUser.getId(), userPlaceDto);

        User updatedAfterLoginUser = service.login("test@naver.com", "password");

        Assertions.assertThat(loginUser.getUserPlace().getCity()).isNotEqualTo("서울");

        Assertions.assertThat(updatedAfterLoginUser.getUserPlace().getCity()).isEqualTo("서울");

    }
}

