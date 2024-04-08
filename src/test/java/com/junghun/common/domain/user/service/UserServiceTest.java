package com.junghun.common.domain.user.service;


import com.junghun.common.domain.user.dto.UserRegisterDto;
import com.junghun.common.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@Slf4j
@ContextConfiguration(classes = TestConfig.class)
class UserServiceTest {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserService service;

    static int testId = 0;

    @BeforeEach
    void setUser(){
        testId++;

        Map<String,Object> userPlace = new HashMap<>();
        userPlace.put("city","세종");
        userPlace.put("county","한솔동");
        userPlace.put("dong","전체");

        List<String> interestCategory = new ArrayList<>();
        interestCategory.add("sports");
        interestCategory.add("language");
        interestCategory.add("game");

        UserRegisterDto registerDto =  UserRegisterDto.builder()
                .email("test"+testId+"@naver.com")
                .name("test"+testId)
                .password("password")
                .gender("남성")
                .birthday(LocalDate.of(1999,testId,16))
                .userPlace(userPlace)
                .interestCategory(interestCategory)
                .profileImage("image")
                .information("information")
                .build();

        service.registerUser(registerDto);
    }


    @Test
    void loginAndUpdateUser() {

        User loginUser = service.loginUser("test1@naver.com","password");

        service.updatePassword(loginUser.getId(),"password2");

        User updatedUser = service.findUserById(loginUser.getId()).get();

        Assertions.assertThat(loginUser.getId()).isEqualTo(updatedUser.getId());

        Assertions.assertThat(loginUser.getPassword()).isNotEqualTo(updatedUser.getPassword());
    }

    @Test
    void resetPassword() {

        service.resetPassword("test1@naver.com","password123");

        User loginUser = service.loginUser("test1@naver.com","password123");

        Assertions.assertThat(loginUser).isNotNull();

        Assertions.assertThat(loginUser.getPassword()).isNotEqualTo("password");

        Assertions.assertThat(loginUser.getId()).isEqualTo(1L);
    }
}

@TestConfiguration
class TestConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}