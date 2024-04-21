package com.junghun.common.domain.daily.controller;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.entity.Comments;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.daily.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService service;

    @PutMapping("/upload")
    public ResponseEntity<Comments> upload(@RequestBody CommentUploadDto commentUploadDto) {
        Comments comment = service.upload(commentUploadDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comments> findById(@PathVariable Long commentId) {
        Comments comment = service.findById(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Reply>> findRepliesById(@PathVariable Long commentId) {
        Comments comment = service.findById(commentId);
        return ResponseEntity.ok(comment.getReplyList());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comments> update(@PathVariable Long commentId, @RequestBody CommentUpdateDto commentUpdateDto) {
        Comments comment = service.update(commentId,commentUpdateDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Comments> delete(@PathVariable Long commentId) {
        service.deleteById(commentId);
        return ResponseEntity.ok().build();
    }
}
