package com.junghun.common.domain.gathering.model;

import com.junghun.common.util.ConvertUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "club_gathering")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubGathering extends Gathering {

    @Column(name = "city_list")
    protected String cityList;

    public List<String> getCityList() {
        return ConvertUtils.getListByString(cityList);
    }
}
