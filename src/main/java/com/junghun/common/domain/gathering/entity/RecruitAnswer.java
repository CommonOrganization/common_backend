package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.gathering.GatheringType;
import com.junghun.common.domain.user.entity.User;
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

    @Column(name = "gathering_type")
    private GatheringType gatheringType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applier_id", referencedColumnName = "id")
    private User applier;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "time_stamp", nullable = false)
    private LocalDateTime timeStamp;
}