package com.junghun.common.domain.gathering.repository;

import com.junghun.common.domain.gathering.entity.OneDayGatheringApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayGatheringApplyStatusRepository extends JpaRepository<OneDayGatheringApplyStatus, Long> {

    List<OneDayGatheringApplyStatus> findByApplierId(Long applierId);
}
