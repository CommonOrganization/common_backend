package com.junghun.common.domain.gathering.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClubGatheringUpdateDto {

    private String category;
    private String detailCategory;
    private String title;
    private String content;
    private String mainImage;
    private List<String> imageList;
    private String recruitWay;
    private String recruitQuestion;
    private int capacity;
    private List<String> tagList;

    private List<String> cityList;
}
