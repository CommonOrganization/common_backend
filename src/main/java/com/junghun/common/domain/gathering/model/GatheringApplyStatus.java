package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gathering_apply_status")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GatheringApplyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "applier_id")
    private Long applierId;

    @Column(name = "gathering_id")
    private Long gatheringId;

    @Column(name = "status")
    private boolean status;

    @Column(name = "gathering_type")
    @Enumerated(EnumType.STRING)
    private GatheringType gatheringType;

    public boolean getStatus(){
        return this.status;
    }
}
