package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayGatheringApplyStatusRepository extends JpaRepository<OneDayGatheringApplyStatus, Long> {

    List<OneDayGatheringApplyStatus> findByApplierIdAndOneDayGatheringId(Long applierId, Long oneDayGatheringId);

    @Query("SELECT oa FROM OneDayGatheringApplyStatus oa " +
            "WHERE oa.id = :oneDayGatheringId " +
            "AND oa.status = true")
    List<OneDayGatheringApplyStatus> findApprovedApplies(Long oneDayGatheringId);
}
