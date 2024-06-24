package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    List<Daily> findByOrderByTimeStampDesc();

    List<Daily> findByWriterIdOrderByTimeStampDesc(Long writerId);

    List<Daily> findByClubGatheringIdOrderByTimeStampDesc(Long clubGatheringId);

    List<Daily> findByCategoryOrderByTimeStampDesc(String category);

    @Query("SELECT d FROM Daily d WHERE d.detailCategory LIKE %:keyword% " +
            "OR d.category LIKE %:keyword% " +
            "OR d.content LIKE %:keyword% " +
            "OR d.tagList LIKE %:keyword% " +
            "ORDER BY d.timeStamp DESC ")
    List<Daily> findByKeywordByTimeStampDesc(String keyword);

    @Modifying
    @Query("DELETE FROM Daily d WHERE d.clubGathering.id = :clubGatheringId")
    void deleteDailiesByClubGathering(Long clubGatheringId);
}
