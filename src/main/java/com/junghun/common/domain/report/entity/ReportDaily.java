package com.junghun.common.domain.report.entity;

import com.junghun.common.domain.daily.entity.Daily;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_daily")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_daily_id", referencedColumnName = "id")
    private Daily reportedDaily;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
