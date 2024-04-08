package com.junghun.common.domain.likeobject.entity;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_object")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @ManyToOne
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering oneDayGathering;

    @ManyToOne
    @JoinColumn(name = "daily_id", referencedColumnName = "id")
    private Daily daily;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}

