package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gathering_apply_status")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GatheringApplyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applier_id", referencedColumnName = "id")
    private User applier;

    @Column(name = "status",columnDefinition = "boolean default false")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;
}
