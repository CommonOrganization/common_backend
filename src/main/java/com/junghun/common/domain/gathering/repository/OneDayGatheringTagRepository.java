package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.OneDayGatheringImage;
import com.junghun.common.domain.gathering.entity.OneDayGatheringTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayGatheringTagRepository extends JpaRepository<OneDayGatheringTag, Long> {
    List<OneDayGatheringTag> findByOneDayGatheringId(Long oneDayGatheringId);
}