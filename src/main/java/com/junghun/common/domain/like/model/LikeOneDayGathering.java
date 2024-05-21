package com.junghun.common.domain.like.model;

import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_one_day_gathering")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeOneDayGathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;


    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}

