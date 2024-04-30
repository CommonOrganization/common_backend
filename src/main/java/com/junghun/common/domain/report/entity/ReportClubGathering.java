
package com.junghun.common.domain.report.entity;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_club_gathering")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReportClubGathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_club_gathering_id", referencedColumnName = "id")
    private ClubGathering reportedClubGathering;


    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
