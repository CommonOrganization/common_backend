package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club_gathering")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClubGathering extends Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ListConverter.class)
    @Column(name = "city")
    private final List<String> cityList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OneDayGathering> oneGatheringList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Daily> dailyList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GatheringApplyStatus> applyStatusList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<RecruitAnswer> recruitAnswerList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeClubGathering> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedClubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Report> reportedList = new ArrayList<>();
}
