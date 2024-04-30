package com.junghun.common.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterWrapper {
    private RegisterDto registerDto;
    private UserPlaceDto userPlaceDto;
}
