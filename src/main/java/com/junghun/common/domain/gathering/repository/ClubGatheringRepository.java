package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubGatheringRepository extends JpaRepository<ClubGathering, Long> {
}