package com.junghun.common.domain.daily.dto;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DailyUploadDto {
    private Long writerId;
    private String category;
    private String detailCategory;
    private String dailyType;
    private Long clubGatheringId;
    private String mainImage;
    private List<String> imageList;
    private String content;
    private List<String> tagList;
}