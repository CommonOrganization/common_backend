package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.OneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayGatheringRepository extends JpaRepository<OneDayGathering, Long> {

    List<OneDayGathering> findByManagerId(Long managerId);
    List<OneDayGathering> findByClubGatheringId(Long clubGatheringId);

    @Query("SELECT o FROM OneDayGathering o " +
            "JOIN OneDayGatheringApplyStatus oa on o.id = oa.oneDayGathering.id " +
            "WHERE oa.applier.id = :applierId")
    List<OneDayGathering> findByApplierId(Long applierId);
}