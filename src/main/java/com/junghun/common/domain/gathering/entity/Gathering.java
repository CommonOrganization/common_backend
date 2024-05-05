package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.user.entity.User;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    protected User manager;

    @Column(name = "category", length = 30)
    protected String category;

    @Column(name = "detail_category", length = 30)
    protected String detailCategory;

    @Column(name = "title", length = 100)
    protected String title;

    @Column(name = "content", length = 1000)
    protected String content;

    @Column(name = "main_image")
    protected String mainImage;

    @Builder.Default
    @Convert(converter = ListConverter.class)
    @Column(name = "image_list")
    protected List<String> imageList = new ArrayList<>();

    @Column(name = "recruit_way", length = 30)
    protected String recruitWay;

    @Column(name = "recruit_question")
    protected String recruitQuestion;

    @Builder.Default
    @Column(name = "capacity")
    protected int capacity = 10;

    @Builder.Default
    @Convert(converter = ListConverter.class)
    @Column(name = "tag_list")
    protected List<String> tagList = new ArrayList<>();

    @Column(name = "time_stamp")
    protected LocalDateTime timeStamp;

}
