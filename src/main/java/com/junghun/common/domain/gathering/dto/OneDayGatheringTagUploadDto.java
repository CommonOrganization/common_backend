package com.junghun.common.domain.gathering.dto;

import com.junghun.common.domain.gathering.entity.OneDayGathering;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OneDayGatheringTagUploadDto {
    private OneDayGathering oneDayGathering;
    private List<String> tagList;
}
