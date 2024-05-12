package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.gathering.entity.OneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OneDayGatheringRepository extends JpaRepository<OneDayGathering, Long> {

    List<OneDayGathering> findByManagerIdOrderByTimeStampDesc(Long managerId);

    List<OneDayGathering> findByClubGatheringIdOrderByTimeStampDesc(Long clubGatheringId);

    @Query("SELECT o FROM OneDayGathering o " +
            "JOIN OneDayGatheringApplyStatus oa on o.id = oa.oneDayGathering.id " +
            "WHERE oa.applier.id = :applierId " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByApplierId(Long applierId);

    @Query("SELECT o FROM OneDayGathering o " +
            "JOIN OneDayGatheringApplyStatus oa on o.id = oa.oneDayGathering.id " +
            "WHERE oa.applier.id = :applierId " +
            "and oa.status = true " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findParticipateInGatheringByApplierId(Long applierId);

    @Query("SELECT o FROM OneDayGathering o " +
            "WHERE o.openingDate >= :now " +
            "AND o.openingDate < :endDate " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findWithDateRange(LocalDateTime now, LocalDateTime endDate);

    @Query("SELECT o FROM OneDayGathering o " +
            "WHERE o.category IN :categories " +
            "OR o.detailCategory IN :categories " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByCategoriesIn(String[] categories);

    @Query("SELECT o FROM OneDayGathering o " +
            "WHERE o.category = :category " +
            "OR o.detailCategory = :category " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByCategory(String category);

    @Query("SELECT o FROM OneDayGathering o " +
            "WHERE o.location LIKE %:city% " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByCity(String city);

    @Query("SELECT o FROM OneDayGathering o WHERE o.detailCategory LIKE %:keyword% " +
            "OR o.content LIKE %:keyword% " +
            "OR o.title LIKE %:keyword% " +
            "OR o.content LIKE %:keyword% " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByKeyword(String keyword);

}