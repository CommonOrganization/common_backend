package com.junghun.common.domain.like.repository;

import com.junghun.common.domain.like.model.LikeObject;
import com.junghun.common.domain.like.model.LikeObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeObjectRepository extends JpaRepository<LikeObject, Long> {

    @Query("SELECT li FROM LikeObject li " +
            "WHERE li.userId = :userId " +
            "AND li.objectId = :objectId " +
            "AND li.objectType = :objectType")
    List<LikeObject> findIsAlreadyLike(Long userId, Long objectId, LikeObjectType objectType);

}
