package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGatheringImage;
import com.junghun.common.domain.gathering.entity.ClubGatheringTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubGatheringImageRepository extends JpaRepository<ClubGatheringImage, Long> {
    List<ClubGatheringImage> findByClubGatheringId(Long oneDayGatheringId);
}