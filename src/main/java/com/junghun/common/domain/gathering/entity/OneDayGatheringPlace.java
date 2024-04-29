package com.junghun.common.domain.gathering.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "one_day_gathering_place")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGatheringPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;

    @Column(name = "city",length = 50)
    private String city;

    @Column(name = "middle_place",length = 50)
    private String middlePlace;

    @Column(name = "detail_place", length = 100)
    private String detailPlace;
}
