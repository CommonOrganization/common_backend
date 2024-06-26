package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.model.LikeDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeDailyRepository extends JpaRepository<LikeDaily, Long> {

    @Query("SELECT ld FROM LikeDaily ld " +
            "WHERE ld.user.id = :userId " +
            "AND ld.daily.id = :dailyId ")
    List<LikeDaily> findIsAlreadyLike(Long userId, Long dailyId);

}
