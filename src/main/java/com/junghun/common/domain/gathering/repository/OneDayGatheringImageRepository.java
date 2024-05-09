package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.OneDayGatheringImage;
import com.junghun.common.domain.gathering.entity.OneDayGatheringPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OneDayGatheringImageRepository extends JpaRepository<OneDayGatheringImage, Long> {
    List<OneDayGatheringImage> findByOneDayGatheringId(Long oneDayGatheringId);
}