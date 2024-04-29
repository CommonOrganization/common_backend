package com.junghun.common.domain.daily.repository;
import com.junghun.common.domain.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {

    List<Daily> findByOrderByTimeStampDesc();
    List<Daily> findByWriterIdOrderByTimeStampDesc(Long writerId);
    List<Daily> findByClubGatheringIdOrderByTimeStampDesc(Long clubGatheringId);
    List<Daily> findByCategoryOrderByTimeStampDesc(String category);
    @Query("SELECT d FROM Daily d WHERE d.tagList LIKE %:keyword% " +
            "OR d.detailCategory LIKE %:keyword% " +
            "OR d.content LIKE %:keyword% "+
            "ORDER BY d.timeStamp DESC")
    List<Daily> findByKeywordByTimeStampDesc(String keyword);
}
