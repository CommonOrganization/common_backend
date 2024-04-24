package com.junghun.common.domain.gathering.service;

import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.gathering.entity.ClubGatheringApplyStatus;
import com.junghun.common.domain.gathering.exception.AlreadyApplyGatheringException;
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

    public void applyGathering(Long applierId,Long clubGatheringId) {

        List<ClubGatheringApplyStatus> oneDayGatheringApplyStatusList = repository.findByApplierIdAndClubGatheringId(applierId,clubGatheringId);

        if(!oneDayGatheringApplyStatusList.isEmpty()){
            throw new AlreadyApplyGatheringException("이미 신청중이거나, 승인된 모임입니다.");
        }

        User applier = userService.findById(applierId);
        ClubGathering clubGathering = clubGatheringService.findById(clubGatheringId);

        ClubGatheringApplyStatus clubGatheringApplyStatus = ClubGatheringApplyStatus.builder()
                .applier(applier)
                .clubGathering(clubGathering)
                .status(clubGathering.getRecruitWay().equals("firstCome"))
                .build();

         repository.save(clubGatheringApplyStatus);
    }

    public void approveGathering(Long applierId,Long clubGatheringId) {
        List<ClubGatheringApplyStatus> clubGatheringApplyStatusList = repository.findByApplierIdAndClubGatheringId(applierId,clubGatheringId);

        for(ClubGatheringApplyStatus clubGatheringApplyStatus : clubGatheringApplyStatusList){

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
