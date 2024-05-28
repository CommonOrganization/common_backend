package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recruit_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecruitAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gathering_id")
    private Long gatheringId;

    @Column(name = "applier_id")
    private Long applier_id;

    @Column(name = "gathering_type")
    @Enumerated(EnumType.STRING)
    private GatheringType gatheringType;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;
}