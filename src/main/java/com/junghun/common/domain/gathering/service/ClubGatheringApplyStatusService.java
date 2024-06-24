package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.exception.*;
import com.junghun.common.domain.gathering.model.*;
import com.junghun.common.domain.gathering.repository.ClubGatheringApplyStatusRepository;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringApplyStatusService {

    private final ClubGatheringApplyStatusRepository repository;
    private final UserRepository userRepository;
    private final ClubGatheringRepository clubGatheringRepository;

    public ClubGatheringApplyStatus applyGathering(Long applierId, Long gatheringId) {

        User applier = userRepository.findById(applierId).orElseThrow(() -> new NotFoundUserException(applierId + "를 가진 이용자가 없습니다."));
        ClubGathering clubGathering = clubGatheringRepository.findById(gatheringId).orElseThrow(() -> new NotFoundGatheringException(gatheringId + "를 가진 하루모임이 존재하지 않습니다."));

        validAlreadyApplied(applierId, gatheringId);

        boolean status = getStatus(applierId, gatheringId);

        ClubGatheringApplyStatus gatheringApplyStatus = ClubGatheringApplyStatus.builder()
                .applier(applier)
                .clubGathering(clubGathering)
                .status(status)
                .build();

        return repository.save(gatheringApplyStatus);
    }

    public void approveGathering(Long statusId) {
        ClubGatheringApplyStatus gatheringApplyStatus = repository.findById(statusId).orElseThrow(() -> new NotFoundGatheringApplyStatusException(statusId + "를 가진 신청이 존재하지 않습니다."));

        ClubGatheringApplyStatus updateGatheringApplyStatus = ClubGatheringApplyStatus.builder()
                .id(gatheringApplyStatus.getId())
                .status(true)
                .applier(gatheringApplyStatus.getApplier())
                .clubGathering(gatheringApplyStatus.getClubGathering())
                .build();

        repository.save(updateGatheringApplyStatus);
    }

    public void refuseApplyGathering(Long id) {
        repository.deleteById(id);
    }

    public ClubGatheringApplyStatus findStatus(Long statusId) {
        return repository.findById(statusId).orElseThrow(() -> new NotFoundGatheringApplyStatusException(statusId + "를 가진 신청이 존재하지 않습니다."));
    }

    public void deleteAll() {
        List<ClubGatheringApplyStatus> gatheringApplyStatusList = repository.findAll();
        for (ClubGatheringApplyStatus gatheringApplyStatus : gatheringApplyStatusList) {
            repository.deleteById(gatheringApplyStatus.getId());
        }
    }

    private void validAlreadyApplied(Long applierId, Long gatheringId) {
        List<ClubGatheringApplyStatus> gatheringApplyStatusList = repository.findByApplierAndGathering(applierId, gatheringId);

        if (!gatheringApplyStatusList.isEmpty()) {
            throw new AlreadyApplyGatheringException("이미 신청중이거나, 승인된 모임입니다.");
        }

    }

    private boolean getStatus(Long applierId, Long gatheringId) {
        List<ClubGatheringApplyStatus> approvedApplies = repository.findApprovedApplies(gatheringId);

        User applier = userRepository.findById(applierId).orElseThrow(() -> new NotFoundUserException(applierId + "를 가진 이용자가 없습니다."));

        boolean status;
        int capacity;
        ClubGathering clubGathering = clubGatheringRepository.findById(gatheringId).orElseThrow(() -> new NotFoundGatheringException(gatheringId + "를 가진 하루모임이 존재하지 않습니다."));
        status = clubGathering.getRecruitWay() == RecruitWay.FirstCome;
        capacity = clubGathering.getCapacity();
        if (!clubGathering.getCityList().contains(applier.getLocation().get("city"))) {
            throw new NotProvideServiceLocationException("현재 이용자의 위치에서는 하루모임에 참여할 수 없습니다.");
        }

        if (capacity <= approvedApplies.size()) {
            throw new FullGatheringException("참여자가 많아 더이상 참여신청을 할 수 없습니다.");
        }

        return status;
    }
}
