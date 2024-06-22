package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.GatheringType;
import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayGatheringApplyStatusRepository extends JpaRepository<OneDayGatheringApplyStatus, Long> {

    @Query("SELECT oga FROM OneDayGatheringApplyStatus oga " +
            "WHERE oga.id = :gatheringId " +
            "AND oga.applier.id = :applierId")
    List<OneDayGatheringApplyStatus> findByApplierAndGathering(Long applierId, Long gatheringId);

    @Query("SELECT oga FROM OneDayGatheringApplyStatus oga " +
            "WHERE oga.id = :gatheringId " +
            "AND oga.applier.id = :applierId "+
            "AND oga.status = false")
    List<OneDayGatheringApplyStatus> findNoApprovedApplies(Long applierId, Long gatheringId);

    @Query("SELECT oga FROM OneDayGatheringApplyStatus oga " +
            "WHERE oga.id = :gatheringId " +
            "AND oga.status = true")
    List<OneDayGatheringApplyStatus> findApprovedApplies(Long gatheringId);
}
