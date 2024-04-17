package com.junghun.common.domain.user.service;


import com.junghun.common.config.MainConfig;
import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    void loginAndUpdateUser() {

        User loginUser = service.login("test@naver.com","password");

        service.updatePassword(loginUser.getId(),"password2");

        User updatedUser = service.findById(loginUser.getId());

        Assertions.assertThat(loginUser.getId()).isEqualTo(updatedUser.getId());

        Assertions.assertThat(loginUser.getPassword()).isNotEqualTo(updatedUser.getPassword());
    }

    @Test
    void resetPassword() {

        service.resetPassword("test@naver.com","password123");

        User loginFailedUser = service.login("test@naver.com","password");

        Assertions.assertThat(loginFailedUser).isNull();

        User loginSuccessUser = service.login("test@naver.com","password123");

        Assertions.assertThat(loginSuccessUser.getName()).isEqualTo("test");
    }

    @Test
    void getInformation(){

        User user = service.login("test@naver.com","password");

        InformationDto informationDto = service.findInformationById(user.getId());

        Assertions.assertThat(informationDto.getName()).isEqualTo("test");
    }
}

