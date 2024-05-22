package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.model.GatheringType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringRepository extends JpaRepository<ClubGathering, Long> {
    List<ClubGathering> findByManagerIdOrderByTimeStampDesc(Long managerId);

    @Query("SELECT c FROM ClubGathering c " +
            "JOIN GatheringApplyStatus ga on ga.gatheringType = :gatheringType AND c.id = ga.gatheringId " +
            "WHERE ga.applierId = :applierId " +
            "AND ga.status = true " +
            "ORDER BY c.timeStamp DESC")
    List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId, GatheringType gatheringType);

    @Query("SELECT c, COUNT(lc) AS like_count " +
            "FROM ClubGathering c " +
            "JOIN LikeClubGathering lc ON c.id = lc.clubGathering.id " +
            "WHERE c.cityList LIKE %:city% " +
            "GROUP BY c " +
            "ORDER BY like_count DESC")
    List<ClubGathering> findTrendGathering(String city);

    @Query("SELECT c.category FROM ClubGathering c " +
            "WHERE c.cityList LIKE %:city% " +
            "AND c.category IN (:categoryList) " +
            "GROUP BY c.category " +
            "HAVING COUNT(c) >= 3")
    List<String> filterRankingCategories(String city, String categoryList);

    @Query("SELECT c FROM ClubGathering c " +
            "WHERE c.category = :category " +
            "OR c.detailCategory = :category " +
            "AND c.cityList LIKE %:city% " +
            "ORDER BY c.timeStamp DESC")
    List<ClubGathering> findByCategory(String city, String category);

    @Query("SELECT c FROM ClubGathering c WHERE c.detailCategory LIKE %:keyword% " +
            "OR c.content LIKE %:keyword% " +
            "OR c.title LIKE %:keyword% " +
            "OR c.content LIKE %:keyword% " +
            "AND c.cityList LIKE %:city% " +
            "ORDER BY c.timeStamp DESC")
    List<ClubGathering> findByKeyword(String city, String keyword);
}