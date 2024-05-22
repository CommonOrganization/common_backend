package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.GatheringApplyStatus;
import com.junghun.common.domain.gathering.model.GatheringType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringApplyStatusRepository extends JpaRepository<GatheringApplyStatus, Long> {

    @Query("SELECT ga FROM GatheringApplyStatus ga " +
            "WHERE ga.id = :gatheringId " +
            "AND ga.gatheringType = :gatheringType " +
            "AND ga.applierId = :applierId")
    List<GatheringApplyStatus> findByApplierAndGathering(Long applierId, Long gatheringId,GatheringType gatheringType);

    @Query("SELECT ga FROM GatheringApplyStatus ga " +
            "WHERE ga.id = :gatheringId " +
            "AND ga.gatheringType = :gatheringType " +
            "AND ga.applierId = :applierId "+
            "AND ga.status = false")
    List<GatheringApplyStatus> findNoApprovedApplies(Long applierId, Long gatheringId,GatheringType gatheringType);

    @Query("SELECT ga FROM GatheringApplyStatus ga " +
            "WHERE ga.id = :gatheringId " +
            "AND ga.gatheringType = :gatheringType " +
            "AND ga.status = true")
    List<GatheringApplyStatus> findApprovedApplies(Long gatheringId, GatheringType gatheringType);
}
