package com.junghun.common.domain.gathering.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club_gathering_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubGatheringImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_Gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;
}
