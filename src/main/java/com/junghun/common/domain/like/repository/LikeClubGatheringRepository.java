package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.model.LikeClubGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeClubGatheringRepository extends JpaRepository<LikeClubGathering, Long> {
    List<LikeClubGathering> findByUserIdAndClubGatheringId(Long userId,Long clubGatheringId);
}
