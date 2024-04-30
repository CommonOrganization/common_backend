package com.junghun.common.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class RegisterDto {
    private String email;
    private String name;
    private String password;
    private String gender;
    private LocalDate birthday;
    private List<String> interestCategory;
    private String profileImage;
    private String information;

}
