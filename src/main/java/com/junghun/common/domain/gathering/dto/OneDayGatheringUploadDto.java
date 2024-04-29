package com.junghun.common.domain.gathering.dto;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.global.converter.ListConverter;
import com.junghun.common.global.converter.MapConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OneDayGatheringUploadDto {

    private Long managerId;
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

    private String type;
    private LocalDateTime openingDate;
    private boolean haveEntryFee;
    private int entryFee;
    private Long clubGatheringId;
    private boolean showAllThePeople;
}
