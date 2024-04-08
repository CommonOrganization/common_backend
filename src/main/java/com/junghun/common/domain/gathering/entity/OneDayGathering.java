package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.likeobject.entity.LikeObject;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.global.converter.MapConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "one_day_gathering")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGathering extends Gathering {

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Convert(converter = MapConverter.class)
    @Column(name = "place")
    private Map<String, Object> place;

    @Column(name = "have_entry_fee", columnDefinition = "boolean default true")
    private boolean haveEntryFee;

    @Column(name = "entry_fee", columnDefinition = "int default 0")
    private int entryFee;

    @ManyToOne
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @Column(name = "show_all_the_people")
    private boolean showAllThePeople;

    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GatheringApplyStatus> applyStatusList = new ArrayList<>();

    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitAnswer> recruitAnswerList = new ArrayList<>();

    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeObject> likeObjectList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedOneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportedList = new ArrayList<>();
}