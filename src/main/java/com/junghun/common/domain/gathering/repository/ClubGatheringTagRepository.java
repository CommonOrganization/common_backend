package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGatheringTag;
import com.junghun.common.domain.gathering.entity.OneDayGatheringTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringTagRepository extends JpaRepository<ClubGatheringTag, Long> {
    List<ClubGatheringTag> findByClubGatheringId(Long oneDayGatheringId);
}