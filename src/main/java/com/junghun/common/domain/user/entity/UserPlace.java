package com.junghun.common.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_place")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city",length = 50)
    private String city;

    @Column(name = "middle_place",length = 50)
    private String county;

    @Column(name = "detail_place", length = 100)
    private String dong;
}
