package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.exception.NotFoundDailyException;
import com.junghun.common.domain.daily.model.Comment;
import com.junghun.common.domain.daily.model.Daily;
import com.junghun.common.domain.daily.exception.NotFoundCommentsException;
import com.junghun.common.domain.daily.repository.CommentRepository;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.daily.repository.ReplyRepository;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.repository.UserRepository;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    public Comment upload(CommentUploadDto commentUploadDto) {
        User writer = userRepository.findById(commentUploadDto.getWriterId())
                .orElseThrow(() -> new NotFoundUserException(commentUploadDto.getWriterId() + "를 가진 이용자가 존재하지 않습니다."));
        LocalDateTime writeDate = LocalDateTime.now();
        Daily daily = dailyRepository.findById(commentUploadDto.getDailyId())
                .orElseThrow(() -> new NotFoundDailyException(commentUploadDto.getDailyId() + "를 가진 데일리가 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .daily(daily)
                .writer(writer)
                .content(commentUploadDto.getContent())
                .timeStamp(writeDate)
                .build();

        return repository.save(comment);
    }

    public Comment findById(Long commentId) {
        return repository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentsException(commentId + "을(를) 가진 Comment 가 존재하지 않습니다."));
    }

    public Comment update(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentsException(commentId + "을(를) 가진 Comment 가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        Comment updatedComment = Comment.builder()
                .id(commentId)
                .writer(comment.getWriter())
                .timeStamp(writeDate)
                .daily(comment.getDaily())
                .content(commentUpdateDto.getContent())
                .build();

        return repository.save(updatedComment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        repository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentsException(commentId + "을(를) 가진 Comment 가 존재하지 않습니다."));
        replyRepository.deleteRepliesByCommentId(commentId);
        repository.deleteById(commentId);
    }
}
