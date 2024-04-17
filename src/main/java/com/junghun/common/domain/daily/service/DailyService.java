package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.exception.NotFoundCommentException;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.repository.CommentRepository;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.gathering.entity.ClubGathering;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository repository;

    private final UserService userService;

    public Daily upload(DailyUploadDto dailyUploadDto) {
        User writer = userService.findById(dailyUploadDto.getWriterId());

        LocalDateTime writeDate = LocalDateTime.now();

        // TODO 이곳에서 GatheringService 의 getClubGathering 이 완료되면 이곳에서 바꿔주기
        ClubGathering clubGathering = new ClubGathering();
        Daily daily = Daily.builder()
                .writer(writer)
                .category(dailyUploadDto.getCategory())
                .detailCategory(dailyUploadDto.getDetailCategory())
                .dailyType(dailyUploadDto.getDailyType())
                .clubGathering(clubGathering)
                .mainImage(dailyUploadDto.getMainImage())
                .imageList(dailyUploadDto.getImageList())
                .content(dailyUploadDto.getContent())
                .tagList(dailyUploadDto.getTagList())
                .timeStamp(writeDate)
                .build();

        return repository.save(daily);
    }

    public List<Daily> findAll() {
        return repository.findByOrderByTimeStampDesc();
    }

    public Daily findById(Long id) {
        Daily daily = repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
        return daily;
    }

    public List<Daily> findByWriterId(Long writerId) {
        return repository.findByWriterIdOrderByTimeStampDesc(writerId);
    }

    public List<Daily> findByClubGatheringId(Long gatheringId) {
        return repository.findByClubGatheringId(gatheringId);
    }

    public List<Daily> findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    public List<Daily> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public Daily update(Long id, DailyUpdateDto dailyUpdateDto) {
        Daily daily = repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));

        ClubGathering clubGathering = new ClubGathering();

        daily.setCategory(dailyUpdateDto.getCategory());
        daily.setDetailCategory(dailyUpdateDto.getDetailCategory());
        daily.setDailyType(dailyUpdateDto.getDailyType());
        daily.setClubGathering(clubGathering);
        daily.setMainImage(dailyUpdateDto.getMainImage());
        daily.setImageList(dailyUpdateDto.getImageList());
        daily.setContent(dailyUpdateDto.getContent());
        daily.setTagList(dailyUpdateDto.getTagList());

        return repository.save(daily);
    }

    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
        System.out.println("이거 진행되나?");
        repository.deleteById(id);
    }




}
