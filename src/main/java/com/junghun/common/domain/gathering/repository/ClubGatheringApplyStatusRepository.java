package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGatheringApplyStatus;
import com.junghun.common.domain.gathering.entity.OneDayGatheringApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringApplyStatusRepository extends JpaRepository<ClubGatheringApplyStatus, Long> {

    List<ClubGatheringApplyStatus> findByApplierIdAndClubGatheringId(Long applierId, Long clubGatheringId);

    @Query("SELECT ca FROM ClubGatheringApplyStatus ca " +
            "WHERE ca.id = :clubGatheringId " +
            "AND ca.status = true")
    List<ClubGatheringApplyStatus> findApprovedApplies(Long clubGatheringId);
}
