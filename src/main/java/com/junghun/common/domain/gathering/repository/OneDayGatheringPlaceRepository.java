package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.OneDayGatheringPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneDayGatheringPlaceRepository extends JpaRepository<OneDayGatheringPlace, Long> {
    Optional<OneDayGatheringPlace> findByOneDayGatheringId(Long oneDayGatheringId);
}