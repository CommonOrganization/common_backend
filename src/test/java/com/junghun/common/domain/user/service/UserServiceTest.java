package com.junghun.common.domain.user.service;


import com.junghun.common.config.MainConfig;
import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
import com.junghun.common.global.converter.MapConverter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

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
    void setUser(){

        Map<String,Object> userPlace = new HashMap<>();
        userPlace.put("city","세종");
        userPlace.put("county","한솔동");
        userPlace.put("dong","전체");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");

        RegisterDto registerDto =  RegisterDto.builder()
                .email("test@naver.com")
                .name("test")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999,1,16))
                .userPlace(userPlace)
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        service.register(registerDto);
    }

    @AfterEach
    void clearUser(){
        service.resetPassword("test@naver.com","password");
        User user = service.login("test@naver.com","password");
        service.deleteById(user.getId());
    }

    @Test
    @DisplayName("회원가입하고 로그인하기")
    void register(){
        // given
        Map<String,Object> userPlace = new HashMap<>();
        userPlace.put("city","세종");
        userPlace.put("county","한솔동");
        userPlace.put("dong","전체");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");

        RegisterDto registerDto =  RegisterDto.builder()
                .email("register@naver.com")
                .name("가입자")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999,1,16))
                .userPlace(userPlace)
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        // when
        service.register(registerDto);

        User user = service.login("register@naver.com","password");
        // then
        InformationDto userInformation = service.findInformationById(user.getId());

        Assertions.assertThat(userInformation.getName()).isEqualTo("가입자");
    }

    @Test
    @DisplayName("정보 수정 테스트")
    void loginAndUpdateUser() {
        //given
        User loginUser = service.login("test@naver.com","password");

        InformationDto informationDto = new InformationDto();
        informationDto.setName("가입자");
        informationDto.setInformation("이건 회원정보");
        informationDto.setProfileImage("helloImage");

        Map<String,Object> userPlace = new HashMap<>();
        userPlace.put("city","대전");
        userPlace.put("county","유성구");
        userPlace.put("dong","궁동");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("study");
        interestCategory.add("game");

        // when
        service.updatePassword(loginUser.getId(),"password2");
        service.updateInformation(loginUser.getId(),informationDto);
        service.updateUserPlace(loginUser.getId(),userPlace);
        service.updateInterestCategory(loginUser.getId(),interestCategory);

        User updatedUser = service.login("test@naver.com","password2");

        // then
        Assertions.assertThat(updatedUser.getName()).isEqualTo("가입자");
        Assertions.assertThat(updatedUser.getInformation()).isEqualTo("이건 회원정보");
        Assertions.assertThat(updatedUser.getInformation()).isEqualTo("이건 회원정보");
        Assertions.assertThat(updatedUser.getUserPlace().get("city")).isEqualTo("대전");
        Assertions.assertThat(updatedUser.getInterestCategory()).containsExactly("study","game");
    }

    @Test
    @DisplayName("비밀번호 초기화 테스트")
    void resetPassword() {

        service.resetPassword("test@naver.com","password123");

        User loginFailedUser = service.login("test@naver.com","password");

        Assertions.assertThat(loginFailedUser).isNull();

        User loginSuccessUser = service.login("test@naver.com","password123");

        Assertions.assertThat(loginSuccessUser.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("이메일 중복 테스트")
    void duplicatedEmail() {

        Map<String,Object> userPlace = new HashMap<>();
        userPlace.put("city","세종");
        userPlace.put("county","한솔동");
        userPlace.put("dong","전체");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");

        RegisterDto registerDto =  RegisterDto.builder()
                .email("test@naver.com")
                .name("test")
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999,1,16))
                .userPlace(userPlace)
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        Assertions.assertThatThrownBy(()->service.register(registerDto)).isInstanceOf(DuplicatedEmailException.class);
    }
}

