package com.junghun.common.domain.user.entity;

import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import com.junghun.common.domain.like.entity.LikeClubGathering;
import com.junghun.common.domain.like.entity.LikeDaily;
import com.junghun.common.domain.like.entity.LikeOneDayGathering;
import com.junghun.common.util.ConvertUtils;
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

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "information")
    private String information;

    @Column(name = "location")
    private String location;

    @Column(name = "category_list")
    private String categoryList;

    // 모임 관련 JOIN 컬럼
    @Builder.Default
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OneDayGathering> oneDayGatheringList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubGathering> clubGatheringList = new ArrayList<>();

    // 데일리, 댓글, 대댓글 관련 JOIN 컬럼
    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailyList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    // 즐겨찾기 관련 JOIN 컬럼
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeOneDayGathering> likeOneDayGatheringList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeClubGathering> likeClubGatheringList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeDaily> likeDailyList = new ArrayList<>();

    public Map<String, String> getLocation() {
        return ConvertUtils.getMapByString(location);
    }

    public List<String> getCategoryList() {
        return ConvertUtils.getListByString(categoryList);
    }
}