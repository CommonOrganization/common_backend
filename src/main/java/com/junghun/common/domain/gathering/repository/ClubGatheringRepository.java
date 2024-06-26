package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.ClubGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringRepository extends JpaRepository<ClubGathering, Long> {
    List<ClubGathering> findByManagerIdOrderByTimeStampDesc(Long managerId);

    @Query("SELECT c FROM ClubGathering c " +
            "JOIN ClubGatheringApplyStatus cga on c.id = cga.clubGathering.id " +
            "WHERE cga.applier.id = :applierId " +
            "AND cga.status = true " +
            "ORDER BY c.timeStamp DESC")
    List<ClubGathering> findParticipateInGatheringByApplierId(Long applierId);

    @Query(value = "SELECT c.*, cnt FROM club_gathering c " +
            "LEFT JOIN (SELECT lc.id,count(lc.id) AS cnt from like_club_gathering lc GROUP BY lc.id) AS slc " +
            "ON c.id = slc.id " +
            "WHERE c.city_list LIKE %:city% " +
            "GROUP BY c.id " +
            "ORDER BY cnt DESC, c.id ASC", nativeQuery = true)
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