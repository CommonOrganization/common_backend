package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.entity.DailyImage;
import com.junghun.common.domain.daily.entity.DailyTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyTagRepository extends JpaRepository<DailyTag, Long> {

    List<DailyTag> findByDailyId(Long dailyId);
}
