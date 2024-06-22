package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.ClubGatheringApplyStatus;
import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringApplyStatusRepository extends JpaRepository<ClubGatheringApplyStatus, Long> {

    @Query("SELECT cga FROM ClubGatheringApplyStatus cga " +
            "WHERE cga.id = :gatheringId " +
            "AND cga.applier.id = :applierId")
    List<ClubGatheringApplyStatus> findByApplierAndGathering(Long applierId, Long gatheringId);

    @Query("SELECT cga FROM ClubGatheringApplyStatus cga " +
            "WHERE cga.id = :gatheringId " +
            "AND cga.applier.id = :applierId "+
            "AND cga.status = false")
    List<ClubGatheringApplyStatus> findNoApprovedApplies(Long applierId, Long gatheringId);

    @Query("SELECT cga FROM ClubGatheringApplyStatus cga " +
            "WHERE cga.id = :gatheringId " +
            "AND cga.status = true")
    List<ClubGatheringApplyStatus> findApprovedApplies(Long gatheringId);
}
