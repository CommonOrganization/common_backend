package com.junghun.common.domain.report.model;

import com.junghun.common.domain.gathering.model.OneDayGathering;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_one_day_gathering")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportOneDayGathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering reportedOneDayGathering;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
