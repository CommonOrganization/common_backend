package com.junghun.common.domain.user.entity;

import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.like.entity.LikeDaily;
import com.junghun.common.domain.like.entity.LikeOneDayGathering;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.global.converter.ListConverter;
import com.junghun.common.global.converter.MapConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name",length = 30)
    private String name;

    @Column(name = "password",length = 100)
    private String password;

    @Column(name = "gender",length = 10)
    private String gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Convert(converter = MapConverter.class)
    @Column(name = "user_place")
    private Map<String, Object> userPlace;

    @Convert(converter = ListConverter.class)
    @Column(name = "interest_category")
    private final List<String> interestCategory = new ArrayList<>();

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "information")
    private String information;

    @Column(name = "notification_token")
    private String notificationToken;

    // 모임 관련 JOIN 컬럼
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OneDayGathering> oneDayGatheringList = new ArrayList<>();

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ClubGathering> clubGatheringList = new ArrayList<>();

    // 데일리, 댓글, 대댓글 관련 JOIN 컬럼
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Daily> dailyList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reply> replyList = new ArrayList<>();

    // 즐겨찾기 관련 JOIN 컬럼
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeOneDayGathering> likeOneDayGatheringList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeClubGathering> likeClubGatheringList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeDaily> likeDailyList = new ArrayList<>();

    // 신고 관련 JOIN 컬럼
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Report> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Report> reportedList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserPlace(Map<String, Object> userPlace) {
        this.userPlace = userPlace;
    }

    public void setInterestCategory(List<String> interestCategory) {
        this.interestCategory.clear();
        this.interestCategory.addAll(interestCategory);
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }
}