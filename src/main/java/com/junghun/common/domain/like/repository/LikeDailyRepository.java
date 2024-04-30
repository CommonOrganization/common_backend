package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.daily.entity.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDailyRepository extends JpaRepository<Daily, Long> {
}
