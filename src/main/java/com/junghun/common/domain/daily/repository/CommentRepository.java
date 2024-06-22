package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.daily.id = :dailyId")
    void deleteCommentsByDailyId(Long dailyId);
}
