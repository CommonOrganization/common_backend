package com.junghun.common.domain.gathering.dto;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClubGatheringCityUploadDto {
    private ClubGathering clubGathering;
    private List<String> cityList;
}
