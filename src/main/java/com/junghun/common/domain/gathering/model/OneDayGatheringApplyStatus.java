package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "one_day_gathering_apply_status")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGatheringApplyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applier_id", referencedColumnName = "id")
    private User applier;

    @Builder.Default
    @Column(name = "status")
    private boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;
}
