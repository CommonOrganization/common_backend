package com.junghun.common.domain.user.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserRegisterDto {
    private String email;
    private String name;
    private String password;
    private String gender;
    private LocalDate birthday;
    private Map<String, Object> userPlace;
    private List<String> interestCategory;
    private String profileImage;
    private String information;

}
