package com.junghun.common.domain.gathering.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatheringUpdateWrapper {
    private OneDayGatheringUpdateDto oneDayGatheringUpdateDto;
    private OneDayGatheringPlaceDto oneDayGatheringPlaceDto;
}
