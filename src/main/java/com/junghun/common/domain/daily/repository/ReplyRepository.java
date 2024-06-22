package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.comment.id = :commentId")
    void deleteRepliesByCommentId(Long commentId);

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.comment.daily.id = :dailyId")
    void deleteRepliesByDailyId(Long dailyId);
}
