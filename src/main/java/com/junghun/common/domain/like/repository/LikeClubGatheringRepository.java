package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.entity.LikeClubGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeClubGatheringRepository extends JpaRepository<LikeClubGathering, Long> {
}