package com.junghun.common.domain.daily.entity;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.likeobject.entity.LikeObject;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily")
@Getter
@Setter
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

    @Column(name = "category",length = 30)
    private String category;

    @Column(name = "detail_category",length = 30)
    private String detailCategory;

    @Column(name = "daily_type",length = 30)
    private String dailyType;

    @ManyToOne
    @JoinColumn(name = "club_gathering_id", referencedColumnName = "id")
    private ClubGathering clubGathering;

    @Column(name = "main_image")
    private String mainImage;

    @Convert(converter = ListConverter.class)
    @Column(name = "image_list")
    private List<String> imageList;

    @Column(name = "content",length = 1000)
    private String content;

    @Convert(converter = ListConverter.class)
    @Column(name = "tag_list")
    private List<String> tagList;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeObject> likeObjectList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedDaily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportedList = new ArrayList<>();
}