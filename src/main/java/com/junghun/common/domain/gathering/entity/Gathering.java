package com.junghun.common.domain.gathering.entity;

import com.junghun.common.domain.user.entity.User;
import com.junghun.common.global.converter.ListConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Gathering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "detail_category", length = 30)
    private String detailCategory;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "main_image")
    private String mainImage;

    @Convert(converter = ListConverter.class)
    @Column(name = "image_list")
    private List<String> imageList;

    @Column(name = "recruit_way", length = 30)
    private String recruitWay;

    @Column(name = "recruit_question")
    private String recruitQuestion;

    @Column(name = "capacity", columnDefinition = "int default 10")
    private int capacity;

    @Convert(converter = ListConverter.class)
    @Column(name = "tag_list")
    private List<String> tagList;

    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
}
