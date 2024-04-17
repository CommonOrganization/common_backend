package com.junghun.common.domain.daily.repository;

import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
