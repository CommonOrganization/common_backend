package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.repository.CommentRepository;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.daily.repository.ReplyRepository;
import com.junghun.common.domain.gathering.model.ClubGathering;
import com.junghun.common.domain.gathering.exception.NotFoundGatheringException;
import com.junghun.common.domain.gathering.repository.ClubGatheringRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import com.junghun.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository repository;

    private final UserRepository userRepository;
    private final ClubGatheringRepository clubGatheringRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    // CREATE
    public Daily upload(DailyUploadDto dailyUploadDto) {
        User writer = userRepository.findById(dailyUploadDto.getWriterId())
                .orElseThrow(() -> new NotFoundUserException(dailyUploadDto.getWriterId() + "를 가진 이용자가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (dailyUploadDto.getClubGatheringId() != null) {
            clubGathering = clubGatheringRepository.findById(dailyUploadDto.getClubGatheringId())
                    .orElseThrow(() -> new NotFoundGatheringException(dailyUploadDto.getClubGatheringId() + "를 가진 소모임이 존재하지 않습니다."));
        }

        Daily daily = Daily.builder()
                .writer(writer)
                .category(dailyUploadDto.getCategory())
                .detailCategory(dailyUploadDto.getDetailCategory())
                .dailyType(dailyUploadDto.getDailyType())
                .mainImage(dailyUploadDto.getMainImage())
                .content(dailyUploadDto.getContent())
                .timeStamp(writeDate)
                .imageList(ConvertUtils.getStringByList(dailyUploadDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(dailyUploadDto.getTagList()))
                .clubGathering(clubGathering)
                .build();

        return repository.save(daily);
    }

    // READ
    public List<Daily> findAll() {
        return repository.findByOrderByTimeStampDesc();
    }

    public Daily findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
    }

    public List<Daily> findByWriterId(Long writerId) {
        return repository.findByWriterIdOrderByTimeStampDesc(writerId);
    }

    public List<Daily> findByClubGatheringId(Long gatheringId) {
        return repository.findByClubGatheringIdOrderByTimeStampDesc(gatheringId);
    }

    public List<Daily> findByKeyword(String keyword) {
        return repository.findByKeywordByTimeStampDesc(keyword);
    }

    public List<Daily> findByCategory(String category) {
        return repository.findByCategoryOrderByTimeStampDesc(category);
    }

    // UPDATE
    public Daily update(Long id, DailyUpdateDto dailyUpdateDto) {
        Daily daily = repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        ClubGathering clubGathering = null;
        if (dailyUpdateDto.getClubGatheringId() != null) {
            clubGathering = clubGatheringRepository.findById(dailyUpdateDto.getClubGatheringId())
                    .orElseThrow(() -> new NotFoundGatheringException(dailyUpdateDto.getClubGatheringId() + "를 가진 소모임이 존재하지 않습니다."));
        }

        Daily updateDaily = Daily.builder()
                .id(id)
                .writer(daily.getWriter())
                .category(dailyUpdateDto.getCategory())
                .detailCategory(dailyUpdateDto.getDetailCategory())
                .dailyType(dailyUpdateDto.getDailyType())
                .mainImage(dailyUpdateDto.getMainImage())
                .content(dailyUpdateDto.getContent())
                .timeStamp(writeDate)
                .imageList(ConvertUtils.getStringByList(dailyUpdateDto.getImageList()))
                .tagList(ConvertUtils.getStringByList(dailyUpdateDto.getTagList()))
                .clubGathering(clubGathering)
                .build();

        return repository.save(updateDaily);
    }

    // DELETE
    @Transactional
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundDailyException(id + "을(를) 가진 Daily 가 존재하지 않습니다."));
        replyRepository.deleteRepliesByDailyId(id);
        commentRepository.deleteCommentsByDailyId(id);
        repository.deleteById(id);
    }

}
