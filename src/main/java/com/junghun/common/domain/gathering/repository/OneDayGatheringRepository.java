package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.model.GatheringType;
import com.junghun.common.domain.gathering.model.OneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OneDayGatheringRepository extends JpaRepository<OneDayGathering, Long> {

    List<OneDayGathering> findByManagerIdOrderByTimeStampDesc(Long managerId);

    List<OneDayGathering> findByClubGatheringIdOrderByTimeStampDesc(Long clubGatheringId);

    @Query("SELECT o FROM OneDayGathering o " +
            "JOIN OneDayGatheringApplyStatus oga on o.id = oga.oneDayGathering.id " +
            "WHERE oga.applier.id = :applierId " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findByApplierId(Long applierId);

    @Query("SELECT o FROM OneDayGathering o " +
            "JOIN OneDayGatheringApplyStatus oga on o.id = oga.oneDayGathering.id " +
            "WHERE oga.applier.id = :applierId " +
            "AND oga.status = true ")
    List<OneDayGathering> findParticipateInGatheringByApplierId(Long applierId);

    @Query("SELECT o FROM OneDayGathering o " +
            "WHERE o.openingDate >= :now " +
            "AND o.openingDate < :endDate " +
            "ORDER BY o.timeStamp DESC")
    List<OneDayGathering> findTodayGathering(LocalDateTime now, LocalDateTime endDate);

    @Query(value = "SELECT o.* FROM one_day_gathering o " +
            "LEFT JOIN (SELECT li.object_id,count(li.object_id) AS cnt from like_object li WHERE li.object_type = 'OneDayGathering' GROUP BY li.object_id) AS sli " +
            "ON o.id = sli.object_id " +
            "WHERE o.category = :category " +
            "OR o.detail_category = :category " +
            "GROUP BY o.id " +
            "ORDER BY cnt DESC, o.id ASC", nativeQuery = true)
    List<OneDayGathering> recommendGatheringByCategory(String category);

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