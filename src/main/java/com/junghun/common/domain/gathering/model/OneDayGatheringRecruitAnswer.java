package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "one_day_gathering_recruit_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OneDayGatheringRecruitAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_gathering_id", referencedColumnName = "id")
    private OneDayGathering gathering;

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