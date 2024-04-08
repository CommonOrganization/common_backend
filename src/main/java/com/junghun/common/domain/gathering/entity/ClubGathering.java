package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.likeobject.entity.LikeObject;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club_gathering")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubGathering extends Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = ListConverter.class)
    @Column(name = "city")
    private List<String> cityList;

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGathering> oneGatheringList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailyList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GatheringApplyStatus> applyStatusList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitAnswer> recruitAnswerList = new ArrayList<>();

    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeObject> likeObjectList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedClubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportedList = new ArrayList<>();
}
