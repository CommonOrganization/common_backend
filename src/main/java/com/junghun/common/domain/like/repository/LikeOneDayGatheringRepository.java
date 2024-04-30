package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.entity.LikeOneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeOneDayGatheringRepository extends JpaRepository<LikeOneDayGathering, Long> {
}
