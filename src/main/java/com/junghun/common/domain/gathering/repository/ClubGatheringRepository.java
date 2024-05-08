package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringRepository extends JpaRepository<ClubGathering, Long> {
    List<ClubGathering> findByManagerIdOrderByTimeStampDesc(Long managerId);
    @Query("SELECT c FROM ClubGathering c " +
            "JOIN ClubGatheringApplyStatus ca on c.id = ca.clubGathering.id " +
            "WHERE ca.applier.id = :applierId " +
            "and ca.status = true " +
            "ORDER BY c.timeStamp DESC")
    List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId);

    @Query("SELECT c, COUNT(lc) AS like_count " +
            "FROM ClubGathering c " +
            "LEFT JOIN LikeClubGathering lc ON c.id = lc.clubGathering.id " +
            "GROUP BY c " +
            "ORDER BY like_count DESC")
    List<ClubGathering> findTrendGathering();
}