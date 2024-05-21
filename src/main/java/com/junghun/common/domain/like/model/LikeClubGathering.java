package com.junghun.common.domain.like.model;

import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_club_gathering")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeClubGathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;


    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}

