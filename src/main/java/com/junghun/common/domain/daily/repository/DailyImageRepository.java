package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.entity.DailyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyImageRepository extends JpaRepository<DailyImage, Long> {

    List<DailyImage> findByDailyId(Long dailyId);
}
