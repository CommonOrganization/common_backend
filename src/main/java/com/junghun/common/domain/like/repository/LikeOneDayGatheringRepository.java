package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.model.LikeOneDayGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeOneDayGatheringRepository extends JpaRepository<LikeOneDayGathering, Long> {

    @Query("SELECT lo FROM LikeOneDayGathering lo " +
            "WHERE lo.user.id = :userId " +
            "AND lo.oneDayGathering.id = :oneDayGatheringId ")
    List<LikeOneDayGathering> findIsAlreadyLike(Long userId, Long oneDayGatheringId);

}
