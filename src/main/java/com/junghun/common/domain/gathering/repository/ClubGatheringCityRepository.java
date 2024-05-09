package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGatheringCity;
import com.junghun.common.domain.gathering.entity.ClubGatheringTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringCityRepository extends JpaRepository<ClubGatheringCity, Long> {
    List<ClubGatheringCity> findByClubGatheringId(Long oneDayGatheringId);
}