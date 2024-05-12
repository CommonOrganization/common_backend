package com.junghun.common.domain.user.service;


import com.junghun.common.config.MainConfig;
import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
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

    @BeforeEach
    void setUser() {

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

        service.register(registerDto);
    }

    @AfterEach
    void clearUser() {
        service.resetPassword("register@naver.com", "password");
        User user = service.login("register@naver.com", "password");
        service.deleteById(user.getId());
    }

    @Test
    @DisplayName("회원가입하고 로그인하기")
    void register() {
        // given
        RegisterDto registerDto = new RegisterDto();
        Map<String, String> location = new HashMap<>();

        List<String> categoryList = new ArrayList<>();
        categoryList.add("sports");
        categoryList.add("language");
        categoryList.add("game");

        location.put("city", "세종");
        location.put("middlePlace", "한솔동");
        location.put("detailPlace", "전체");

        registerDto.setEmail("test@naver.com");
        registerDto.setName("가입자");
        registerDto.setPassword("password");
        registerDto.setGender("남성");
        registerDto.setBirthday(LocalDate.of(1999, 1, 16));
        registerDto.setCategoryList(categoryList);
        registerDto.setProfileImage("image");
        registerDto.setInformation("information");
        registerDto.setLocation(location);

        // when
        service.register(registerDto);

        User loginUser = service.login("test@naver.com", "password");
        // then
        InformationDto userInformation = service.findInformationById(loginUser.getId());

        Assertions.assertThat(userInformation.getName()).isEqualTo("가입자");
    }

    @Test
    @DisplayName("정보 수정 테스트")
    void loginAndUpdateUser() {
        //given
        User loginUser = service.login("register@naver.com", "password");

        InformationDto informationDto = new InformationDto();
        informationDto.setName("가입자");
        informationDto.setInformation("이건 회원정보");
        informationDto.setProfileImage("helloImage");

        List<String> categoryList = new ArrayList<>();
        categoryList.add("study");
        categoryList.add("game");

        Map<String, String> location = new HashMap<>();

        location.put("city", "대전");
        location.put("middlePlace", "유성구");
        location.put("detailPlace", "궁동");

        // when
        service.updatePassword(loginUser.getId(), "password2");
        service.updateInformation(loginUser.getId(), informationDto);
        service.updateCategory(loginUser.getId(), categoryList);
        service.updateLocation(loginUser.getId(), location);

        User updatedUser = service.login("register@naver.com", "password2");

        // then
        Assertions.assertThat(loginUser.getCategoryList().size()).isEqualTo(3);
        Assertions.assertThat(updatedUser.getName()).isEqualTo("가입자");
        Assertions.assertThat(updatedUser.getInformation()).isEqualTo("이건 회원정보");
        Assertions.assertThat(updatedUser.getLocation().get("city")).isEqualTo("대전");
        Assertions.assertThat(updatedUser.getCategoryList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("비밀번호 초기화 테스트")
    void resetPassword() {

        service.resetPassword("register@naver.com", "password123");

        User loginFailedUser = service.login("register@naver.com", "password");

        Assertions.assertThat(loginFailedUser).isNull();

        User loginSuccessUser = service.login("register@naver.com", "password123");

        Assertions.assertThat(loginSuccessUser.getName()).isEqualTo("가입자");
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void duplicatedEmail() {
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

        Assertions.assertThatThrownBy(() -> service.register(registerDto)).isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("관심 카테고리 수정 테스트")
    void updateInterestCategory() {

        User loginUser = service.login("register@naver.com", "password");

        List<String> categoryList = new ArrayList<>();
        categoryList.add("sports");
        categoryList.add("language");
        categoryList.add("game");
        categoryList.add("coffee");
        categoryList.add("music");

        service.updateCategory(loginUser.getId(), categoryList);

        User updatedUser = service.login("register@naver.com", "password");

        Assertions.assertThat(updatedUser.getCategoryList()).containsExactly("sports", "language", "game", "coffee", "music");

        Assertions.assertThat(updatedUser.getCategoryList().size()).isEqualTo(5);

    }

    @Test
    @DisplayName("유저 지역 수정 테스트")
    void updateUserPlace() {

        User loginUser = service.login("register@naver.com", "password");

        Map<String, String> location = new HashMap<>();

        location.put("city", "서울");
        location.put("middlePlace", "동작구");
        location.put("detailPlace", "왕천파닭");

        service.updateLocation(loginUser.getId(), location);

        User updatedAfterLoginUser = service.login("register@naver.com", "password");

        Assertions.assertThat(loginUser.getLocation().get("city")).isNotEqualTo("서울");

        Assertions.assertThat(updatedAfterLoginUser.getLocation().get("city")).isEqualTo("서울");

    }
}

