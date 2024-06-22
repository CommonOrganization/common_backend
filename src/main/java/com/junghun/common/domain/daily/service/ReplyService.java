package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.ReplyUpdateDto;
import com.junghun.common.domain.daily.dto.ReplyUploadDto;
import com.junghun.common.domain.daily.model.Comment;
import com.junghun.common.domain.daily.model.Reply;
import com.junghun.common.domain.daily.exception.NotFoundCommentsException;
import com.junghun.common.domain.daily.exception.NotFoundReplyException;
import com.junghun.common.domain.daily.repository.CommentRepository;
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
public class ReplyService {

    private final ReplyRepository repository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Reply upload(ReplyUploadDto replyUploadDto) {
        User writer = userRepository.findById(replyUploadDto.getWriterId())
                .orElseThrow(()->new NotFoundUserException(replyUploadDto.getWriterId()+"를 가진 이용자가 존재하지 않습니다."));
        LocalDateTime writeDate = LocalDateTime.now();
        Comment comment = commentRepository.findById(replyUploadDto.getCommentId())
                .orElseThrow(()->new NotFoundCommentsException(replyUploadDto.getCommentId()+"를 가진 댓글이 존재하지 않습니다."));

        Reply reply = Reply.builder()
                .writer(writer)
                .content(replyUploadDto.getContent())
                .comment(comment)
                .timeStamp(writeDate)
                .build();

        return repository.save(reply);
    }

    public Reply findById(Long replyId) {
        return repository.findById(replyId)
                .orElseThrow(() -> new NotFoundReplyException(replyId + "을(를) 가진 Reply 가 존재하지 않습니다."));
    }

    public Reply update(Long id, ReplyUpdateDto replyUpdateDto) {
        Reply reply = repository.findById(id)
                .orElseThrow(() -> new NotFoundCommentsException(id + "을(를) 가진 Reply 가 존재하지 않습니다."));

        LocalDateTime writeDate = LocalDateTime.now();

        Reply updateReply = Reply.builder()
                .id(id)
                .writer(reply.getWriter())
                .content(replyUpdateDto.getContent())
                .comment(reply.getComment())
                .timeStamp(writeDate)
                .build();

        return repository.save(updateReply);
    }

    @Transactional
    public void deleteById(Long replyId) {
        repository.findById(replyId)
                .orElseThrow(() -> new NotFoundCommentsException(replyId + "을(를) 가진 Reply 가 존재하지 않습니다."));

        repository.deleteById(replyId);
    }
}
