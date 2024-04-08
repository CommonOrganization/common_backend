package com.junghun.common.domain.user.entity;

import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.report.entity.Report;
import com.junghun.common.global.converter.ListConverter;
import com.junghun.common.global.converter.MapConverter;
import jakarta.annotation.Nullable;
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
@Setter
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
    private List<String> interestCategory;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "information")
    private String information;

    @Column(name = "notification_token")
    private String notificationToken;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Daily> dailyList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportList = new ArrayList<>();

    @OneToMany(mappedBy = "reportedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportedList = new ArrayList<>();
}