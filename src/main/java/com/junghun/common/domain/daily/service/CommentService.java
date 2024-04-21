package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.exception.NotFoundCommentsException;
import com.junghun.common.domain.daily.repository.CommentRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final DailyService dailyService;
    private final UserService userService;

    public Comment upload(CommentUploadDto commentUploadDto) {
        User writer = userService.findById(commentUploadDto.getWriterId());
        LocalDateTime writeDate = LocalDateTime.now();
        Daily daily = dailyService.findById(commentUploadDto.getDailyId());

        Comment comment = Comment.builder()
                .daily(daily)
                .writer(writer)
                .content(commentUploadDto.getContent())
                .timeStamp(writeDate)
                .build();

        return repository.save(comment);
    }

    public Comment findById(Long commentId){
        return repository.findById(commentId)
                .orElseThrow(()->new NotFoundCommentsException(commentId+"을(를) 가진 Comment 가 존재하지 않습니다."));
    }

    public Comment update(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = repository.findById(commentId)
                .orElseThrow(()->new NotFoundCommentsException(commentId+"을(를) 가진 Comment 가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        comment.setTimeStamp(writeDate);
        comment.setContent(commentUpdateDto.getContent());

        return repository.save(comment);
    }

    public void deleteById(Long commentId) {
        repository.findById(commentId)
                .orElseThrow(()->new NotFoundCommentsException(commentId+"을(를) 가진 Comment 가 존재하지 않습니다."));

        repository.deleteById(commentId);
    }
}
