package com.junghun.common.domain.gathering.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ClubGatheringUploadDto {

    private Long managerId;
    private String category;
    private String detailCategory;
    private String title;
    private String content;
    private String mainImage;
    private String recruitWay;
    private String recruitQuestion;
    private int capacity;
    private List<String> imageList;
    private List<String> tagList;

    private List<String> cityList;
}
