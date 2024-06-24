package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.model.LikeClubGathering;
import com.junghun.common.domain.like.model.LikeOneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeClubGatheringRepository extends JpaRepository<LikeClubGathering, Long> {

    @Query("SELECT lc FROM LikeClubGathering lc " +
            "WHERE lc.user.id = :userId " +
            "AND lc.clubGathering.id = :clubGatheringId ")
    List<LikeClubGathering> findIsAlreadyLike(Long userId, Long clubGatheringId);

}
