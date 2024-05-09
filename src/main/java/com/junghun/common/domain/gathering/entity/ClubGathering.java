package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.report.entity.ReportClubGathering;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club_gathering")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubGathering extends Gathering {

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubGatheringCity> cityList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubGatheringImage> imageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubGatheringTag> tagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGathering> oneGatheringList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailyList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubGatheringApplyStatus> applyStatusList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitAnswer> recruitAnswerList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeClubGathering> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedClubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportClubGathering> reportedList = new ArrayList<>();
}
