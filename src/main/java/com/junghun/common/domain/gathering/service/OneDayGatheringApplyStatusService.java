package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.model.OneDayGathering;
import com.junghun.common.domain.gathering.model.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.exception.AlreadyApplyGatheringException;
import com.junghun.common.domain.gathering.exception.FullGatheringException;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringApplyStatusException;
import com.junghun.common.domain.gathering.exception.NotProvideServiceLocationException;
import com.junghun.common.domain.gathering.repository.OneDayGatheringApplyStatusRepository;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OneDayGatheringApplyStatusService {

    private final OneDayGatheringApplyStatusRepository repository;
    private final UserService userService;
    private final OneDayGatheringService oneDayGatheringService;

    public void applyGathering(Long applierId, Long oneDayGatheringId) {

        List<OneDayGatheringApplyStatus> oneDayGatheringApplyStatusList = repository.findByApplierIdAndOneDayGatheringId(applierId, oneDayGatheringId);
        if (!oneDayGatheringApplyStatusList.isEmpty()) {
            throw new AlreadyApplyGatheringException("이미 신청중이거나, 승인된 모임입니다.");
        }

        List<OneDayGatheringApplyStatus> approvedApplies = repository.findApprovedApplies(oneDayGatheringId);

        User applier = userService.findById(applierId);
        OneDayGathering oneDayGathering = oneDayGatheringService.findById(oneDayGatheringId);

        if (oneDayGathering.getCapacity() <= approvedApplies.size()) {
            throw new FullGatheringException("참여자가 많아 더이상 참여신청을 할 수 없습니다.");
        }
        if (!applier.getLocation().get("city").equals(oneDayGathering.getLocation().get("city"))) {
            throw new NotProvideServiceLocationException("현재 이용자의 위치에서는 하루모임에 참여할 수 없습니다.");
        }

        OneDayGatheringApplyStatus oneDayGatheringApplyStatus = OneDayGatheringApplyStatus.builder()
                .applier(applier)
                .oneDayGathering(oneDayGathering)
                .status(oneDayGathering.getRecruitWay().equals("firstCome"))
                .build();

        repository.save(oneDayGatheringApplyStatus);
    }

    public OneDayGatheringApplyStatus findById(Long statusId) {
        return repository.findById(statusId).orElseThrow(() -> new NotFoundGatheringApplyStatusException(statusId + "을(를) 가진 OneDayGatheringApplyStatus 가 존재하지 않습니다."));
    }

    public void approveGathering(Long applierId, Long oneDayGatheringId) {
        List<OneDayGatheringApplyStatus> oneDayGatheringApplyStatusList = repository.findByApplierIdAndOneDayGatheringId(applierId, oneDayGatheringId);

        for (OneDayGatheringApplyStatus oneDayGatheringApplyStatus : oneDayGatheringApplyStatusList) {

            OneDayGatheringApplyStatus updateOneDayGatheringApplyStatus = OneDayGatheringApplyStatus.builder()
                    .id(oneDayGatheringApplyStatus.getId())
                    .applier(oneDayGatheringApplyStatus.getApplier())
                    .oneDayGathering(oneDayGatheringApplyStatus.getOneDayGathering())
                    .status(true)
                    .build();

            repository.save(updateOneDayGatheringApplyStatus);
        }
    }

    public void refuseApplyGathering(Long id) {
        repository.deleteById(id);
    }
}
