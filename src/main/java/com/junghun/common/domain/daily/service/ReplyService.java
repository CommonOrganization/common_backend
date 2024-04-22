package com.junghun.common.domain.daily.service;

import com.junghun.common.domain.daily.dto.ReplyUpdateDto;
import com.junghun.common.domain.daily.dto.ReplyUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.daily.exception.NotFoundCommentsException;
import com.junghun.common.domain.daily.exception.NotFoundReplyException;
import com.junghun.common.domain.daily.repository.ReplyRepository;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository repository;
    private final CommentService commentService;
    private final UserService userService;

    public Reply upload(ReplyUploadDto replyUploadDto) {
        User writer = userService.findById(replyUploadDto.getWriterId());
        LocalDateTime writeDate = LocalDateTime.now();
        Comment comment = commentService.findById(replyUploadDto.getCommentId());

        Reply reply = Reply.builder()
                .writer(writer)
                .content(replyUploadDto.getContent())
                .comment(comment)
                .timeStamp(writeDate)
                .build();

        return repository.save(reply);
    }

    public Reply findById(Long replyId){
        Reply reply = repository.findById(replyId)
                .orElseThrow(()->new NotFoundReplyException(replyId+"을(를) 가진 Reply 가 존재하지 않습니다."));
        return reply;
    }

    public Reply update(Long id, ReplyUpdateDto replyUpdateDto) {
        Reply reply = repository.findById(id)
                .orElseThrow(()->new NotFoundCommentsException(id+"을(를) 가진 Reply 가 존재하지 않습니다."));

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

    public void deleteById(Long replyId) {
        repository.findById(replyId)
                .orElseThrow(()->new NotFoundCommentsException(replyId+"을(를) 가진 Reply 가 존재하지 않습니다."));

        repository.deleteById(replyId);
    }
}
