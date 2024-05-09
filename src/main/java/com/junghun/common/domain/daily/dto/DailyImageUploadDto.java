package com.junghun.common.domain.daily.dto;

import com.junghun.common.domain.daily.entity.Daily;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyImageUploadDto {
    private Daily daily;
    private List<String> imageList;
}
