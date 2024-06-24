package com.junghun.common.domain.gathering.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "club_gathering_recruit_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubGatheringRecruitAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering gathering;

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