package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.ClubGatheringApplyStatus;
import com.junghun.common.domain.gathering.entity.OneDayGatheringApplyStatus;
import com.junghun.common.domain.gathering.exception.AlreadyApplyGatheringException;
import com.junghun.common.domain.gathering.exception.FullGatheringException;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringApplyStatusException;
import com.junghun.common.domain.gathering.exception.NotProvideServiceLocationException;
import com.junghun.common.domain.gathering.repository.ClubGatheringApplyStatusRepository;

import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubGatheringApplyStatusService {

    private final ClubGatheringApplyStatusRepository repository;
    private final UserService userService;
    private final ClubGatheringService clubGatheringService;

    public void applyGathering(Long applierId, Long clubGatheringId) {

        List<ClubGatheringApplyStatus> clubGatheringApplyStatusList = repository.findByApplierIdAndClubGatheringId(applierId, clubGatheringId);

        if (!clubGatheringApplyStatusList.isEmpty()) {
            throw new AlreadyApplyGatheringException("이미 신청중이거나, 승인된 모임입니다.");
        }

        List<ClubGatheringApplyStatus> approvedApplies = repository.findApprovedApplies(clubGatheringId);

        User applier = userService.findById(applierId);
        ClubGathering clubGathering = clubGatheringService.findById(clubGatheringId);

        if (clubGathering.getCapacity() <= approvedApplies.size()) {
            throw new FullGatheringException("참여자가 많아 더이상 참여신청을 할 수 없습니다.");
        }
        if (!clubGathering.getCityList().contains(applier.getLocation().get("city"))) {
            throw new NotProvideServiceLocationException("현재 이용자의 위치에서는 소모임에 참여할 수 없습니다.");
        }

        ClubGatheringApplyStatus clubGatheringApplyStatus = ClubGatheringApplyStatus.builder()
                .applier(applier)
                .clubGathering(clubGathering)
                .status(clubGathering.getRecruitWay().equals("firstCome"))
                .build();

        repository.save(clubGatheringApplyStatus);
    }

    public ClubGatheringApplyStatus findById(Long statusId) {
        return repository.findById(statusId).orElseThrow(() -> new NotFoundGatheringApplyStatusException(statusId + "을(를) 가진 ClubGatheringApplyStatus 가 존재하지 않습니다."));
    }

    public void approveGathering(Long applierId, Long clubGatheringId) {
        List<ClubGatheringApplyStatus> clubGatheringApplyStatusList = repository.findByApplierIdAndClubGatheringId(applierId, clubGatheringId);

        for (ClubGatheringApplyStatus clubGatheringApplyStatus : clubGatheringApplyStatusList) {

            ClubGatheringApplyStatus updateOneDayGatheringApplyStatus = ClubGatheringApplyStatus.builder()
                    .id(clubGatheringApplyStatus.getId())
                    .applier(clubGatheringApplyStatus.getApplier())
                    .clubGathering(clubGatheringApplyStatus.getClubGathering())
                    .status(true)
                    .build();

            repository.save(updateOneDayGatheringApplyStatus);
        }
    }

    public void refuseApplyGathering(Long id) {
        repository.deleteById(id);
    }
}
