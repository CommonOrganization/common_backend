package com.junghun.common.domain.user.model;

import com.junghun.common.util.ConvertUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    public Map<String, String> getLocation() {
        return ConvertUtils.getMapByString(location);
    }

    public List<String> getCategoryList() {
        return ConvertUtils.getListByString(categoryList);
    }
}