package com.junghun.common.domain.report.entity;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", referencedColumnName = "id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", referencedColumnName = "id")
    private User reportedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering reportedOneDayGathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_gathering_id", referencedColumnName = "id")
    private ClubGathering reportedClubGathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_daily_id", referencedColumnName = "id")
    private Daily reportedDaily;


    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
