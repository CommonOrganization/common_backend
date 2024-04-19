package com.junghun.common.domain.daily.entity;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.like.entity.LikeDaily;
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

    @Convert(converter = ListConverter.class)
    @Column(name = "image_list")
    private final List<String> imageList = new ArrayList<>();

    @Column(name = "content", length = 1000)
    private String content;

    @Convert(converter = ListConverter.class)
    @Column(name = "tag_list")
    private final List<String> tagList = new ArrayList<>();

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeDaily> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedDaily", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Report> reportedList = new ArrayList<>();

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setDetailCategory(String detailCategory) {
        this.detailCategory = detailCategory;
    }

    public void setDailyType(String dailyType) {
        this.dailyType = dailyType;
    }

    public void setClubGathering(ClubGathering clubGathering) {
        this.clubGathering = clubGathering;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setImageList(List<String> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTagList(List<String> tagList) {
        this.tagList.clear();
        this.tagList.addAll(tagList);
    }
}