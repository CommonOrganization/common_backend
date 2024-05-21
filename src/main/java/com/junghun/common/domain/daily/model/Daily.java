package com.junghun.common.domain.daily.model;

import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.like.model.LikeDaily;
import com.junghun.common.domain.report.model.ReportDaily;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.util.ConvertUtils;
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

    @Column(name = "tag_list")
    private String tagList;

    @Column(name = "image_list")
    private String imageList;

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeDaily> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reportedDaily", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDaily> reportedList = new ArrayList<>();

    public List<String> getTagList(){
        return ConvertUtils.getListByString(tagList);
    }

    public List<String> getImageList(){
        return ConvertUtils.getListByString(imageList);
    }

}