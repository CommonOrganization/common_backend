package com.junghun.common.domain.daily.controller;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.daily.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PutMapping("/upload")
    public ResponseEntity<Comment> upload(@RequestBody CommentUploadDto commentUploadDto) {
        Comment comment = service.upload(commentUploadDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> findById(@PathVariable Long commentId) {
        Comment comment = service.findById(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Reply>> findRepliesById(@PathVariable Long commentId) {
        Comment comment = service.findById(commentId);
        return ResponseEntity.ok(comment.getReplyList());
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Comment> update(@PathVariable Long commentId, @RequestBody CommentUpdateDto commentUpdateDto) {
        Comment comment = service.update(commentId,commentUpdateDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Comment> delete(@PathVariable Long commentId) {
        service.deleteById(commentId);
        return ResponseEntity.ok().build();
    }
}
