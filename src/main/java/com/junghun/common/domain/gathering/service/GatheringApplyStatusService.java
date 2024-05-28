package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.exception.AlreadyApplyGatheringException;
import com.junghun.common.domain.gathering.exception.FullGatheringException;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringApplyStatusException;
import com.junghun.common.domain.gathering.exception.NotProvideServiceLocationException;
import com.junghun.common.domain.gathering.model.*;
import com.junghun.common.domain.gathering.repository.GatheringApplyStatusRepository;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringApplyStatusService {

    private final GatheringApplyStatusRepository repository;
    private final UserService userService;
    private final ClubGatheringService clubGatheringService;
    private final OneDayGatheringService oneDayGatheringService;

    public GatheringApplyStatus applyGathering(Long applierId, Long gatheringId, GatheringType gatheringType) {

        if(alreadyApplied(applierId,gatheringId,gatheringType)){
            throw new AlreadyApplyGatheringException("이미 신청중이거나, 승인된 모임입니다.");
        }

        boolean status = getStatusByGatheringType(applierId,gatheringId,gatheringType);

        GatheringApplyStatus gatheringApplyStatus = GatheringApplyStatus.builder()
                .applierId(applierId)
                .gatheringId(gatheringId)
                .status(status)
                .gatheringType(gatheringType)
                .build();

        return repository.save(gatheringApplyStatus);
    }

    public GatheringApplyStatus findById(Long statusId) {
        return repository.findById(statusId).orElseThrow(() -> new NotFoundGatheringApplyStatusException(statusId + "을(를) 가진 GatheringApplyStatus 가 존재하지 않습니다."));
    }

    public void approveGathering(Long statusId) {
        GatheringApplyStatus gatheringApplyStatus = repository.findById(statusId).orElseThrow();

        GatheringApplyStatus updateGatheringApplyStatus = GatheringApplyStatus.builder()
                .id(gatheringApplyStatus.getId())
                .status(true)
                .applierId(gatheringApplyStatus.getApplierId())
                .gatheringId(gatheringApplyStatus.getGatheringId())
                .gatheringType(gatheringApplyStatus.getGatheringType())
                .build();

        repository.save(updateGatheringApplyStatus);
    }

    public void refuseApplyGathering(Long id) {
        repository.deleteById(id);
    }


    public void deleteAll() {
        List<GatheringApplyStatus> gatheringApplyStatusList = repository.findAll();
        for (GatheringApplyStatus gatheringApplyStatus : gatheringApplyStatusList) {
            repository.deleteById(gatheringApplyStatus.getId());
        }
    }

    private boolean alreadyApplied(Long applierId,Long gatheringId,GatheringType gatheringType){
        List<GatheringApplyStatus> gatheringApplyStatusList = repository.findByApplierAndGathering(applierId, gatheringId, gatheringType);

        return !gatheringApplyStatusList.isEmpty();
    }

    private boolean getStatusByGatheringType(Long applierId,Long gatheringId,GatheringType gatheringType){
        List<GatheringApplyStatus> approvedApplies = repository.findApprovedApplies(gatheringId, gatheringType);

        User applier = userService.findById(applierId);

        boolean status;
        int capacity;
        if (gatheringType == GatheringType.ClubGathering) {
            ClubGathering clubGathering = clubGatheringService.findById(gatheringId);
            status = clubGathering.getRecruitWay() == RecruitWay.FirstCome;
            capacity = clubGathering.getCapacity();
            if (!clubGathering.getCityList().contains(applier.getLocation().get("city"))) {
                throw new NotProvideServiceLocationException("현재 이용자의 위치에서는 소모임에 참여할 수 없습니다.");
            }
        } else {
            OneDayGathering oneDayGathering = oneDayGatheringService.findById(gatheringId);
            status = oneDayGathering.getRecruitWay() == RecruitWay.FirstCome;
            capacity = oneDayGathering.getCapacity();
            if (!oneDayGathering.getLocation().get("city").equals(applier.getLocation().get("city"))) {
                throw new NotProvideServiceLocationException("현재 이용자의 위치에서는 하루모임에 참여할 수 없습니다.");
            }
        }

        if (capacity <= approvedApplies.size()) {
            throw new FullGatheringException("참여자가 많아 더이상 참여신청을 할 수 없습니다.");
        }

        return status;
    }
}
