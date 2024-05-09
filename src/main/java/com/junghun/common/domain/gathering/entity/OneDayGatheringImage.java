package com.junghun.common.domain.gathering.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "one_day_gathering_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGatheringImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_Gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;
}
