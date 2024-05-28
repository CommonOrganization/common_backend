package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.report.model.ReportClubGathering;
import com.junghun.common.util.ConvertUtils;
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

    @Column(name = "city_list")
    protected String cityList;

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGathering> oneGatheringList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "clubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailyList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedClubGathering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportClubGathering> reportedList = new ArrayList<>();

    public List<String> getCityList() {
        return ConvertUtils.getListByString(cityList);
    }
}
