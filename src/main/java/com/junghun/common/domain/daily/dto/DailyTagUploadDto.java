package com.junghun.common.domain.daily.dto;

import com.junghun.common.domain.daily.entity.Daily;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyTagUploadDto {
    private Daily daily;
    private List<String> tagList;
}
