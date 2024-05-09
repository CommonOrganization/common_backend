package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.like.entity.LikeOneDayGathering;
import com.junghun.common.domain.report.entity.ReportOneDayGathering;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "one_day_gathering")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGathering extends Gathering {
    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Builder.Default
    @Column(name = "have_entry_fee")
    private boolean haveEntryFee = true;

    @Builder.Default
    @Column(name = "entry_fee")
    private int entryFee = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @Builder.Default
    @Column(name = "show_all_the_people")
    private boolean showAllThePeople = false;

    @OneToOne(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private OneDayGatheringPlace place;

    @Builder.Default
    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGatheringImage> imageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGatheringTag> tagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGatheringApplyStatus> applyStatusList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitAnswer> recruitAnswerList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "oneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeOneDayGathering> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedOneDayGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportOneDayGathering> reportedList = new ArrayList<>();
}