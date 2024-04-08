package com.junghun.common.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationDto {
    private Long id;
    private String name;
    private String profileImage;
    private String information;
}
