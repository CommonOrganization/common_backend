package com.junghun.common.domain.daily.model;

import com.junghun.common.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Column(name = "content", length = 1000)
    private String content;

}
