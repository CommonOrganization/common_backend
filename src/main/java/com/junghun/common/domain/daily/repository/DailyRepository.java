package com.junghun.common.domain.daily.repository;
import com.junghun.common.domain.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DailyRepository extends JpaRepository<Daily, Long> {

    List<Daily> findByOrderByTimeStampDesc();
    List<Daily> findByWriterIdOrderByTimeStampDesc(Long writerId);
    List<Daily> findByClubGatheringId(Long clubGatheringId);
    List<Daily> findByCategory(String category);
    @Query("SELECT d FROM Daily d " +
            "WHERE :keyword IN elements(d.tagList) " +
            "OR d.detailCategory LIKE %:keyword% " +
            "OR d.content LIKE %:keyword% "+
            "ORDER BY d.timeStamp DESC")
    List<Daily> findByKeyword(@Param("keyword") String keyword);

}
