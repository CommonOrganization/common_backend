package com.junghun.common.domain.like.model;

import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_object")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_type")
    @Enumerated(EnumType.STRING)
    private LikeObjectType objectType;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}

