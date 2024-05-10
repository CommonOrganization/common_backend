package com.junghun.common.domain.user.dto;

import com.junghun.common.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCategoryDto {
    private User user;
    private List<String> categoryList;
}
