package com.junghun.common.domain.daily.dto;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyUpdateDto {
    private String category;
    private String detailCategory;
    private String dailyType;
    private Long clubGatheringId;
    private String mainImage;
    private List<String> imageList;
    private String content;
    private List<String> tagList;
}