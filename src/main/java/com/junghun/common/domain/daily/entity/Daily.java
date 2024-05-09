package com.junghun.common.domain.daily.entity;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.like.entity.LikeDaily;
import com.junghun.common.domain.report.entity.ReportDaily;
import com.junghun.common.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "detail_category", length = 30)
    private String detailCategory;

    @Column(name = "daily_type", length = 30)
    private String dailyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyTag> tagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyImage> imageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeDaily> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedDaily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDaily> reportedList = new ArrayList<>();
}