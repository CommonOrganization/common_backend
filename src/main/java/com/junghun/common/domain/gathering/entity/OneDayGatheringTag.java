package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.daily.entity.Daily;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "one_day_gathering_tag")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGatheringTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;
}
