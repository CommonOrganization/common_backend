package com.junghun.common.domain.daily.controller;

import com.junghun.common.domain.daily.dto.ReplyUpdateDto;
import com.junghun.common.domain.daily.dto.ReplyUploadDto;
import com.junghun.common.domain.daily.entity.Reply;
import com.junghun.common.domain.daily.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService service;

    @PutMapping("/upload")
    public ResponseEntity<Reply> upload(@RequestBody ReplyUploadDto replyUploadDto) {
        Reply reply = service.upload(replyUploadDto);
        return new ResponseEntity<>(reply, HttpStatus.CREATED);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<Reply> findById(@PathVariable Long replyId) {
        Reply reply = service.findById(replyId);
        return ResponseEntity.ok(reply);
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<Reply> update(@PathVariable Long replyId, @RequestBody ReplyUpdateDto replyUpdateDto) {
        Reply reply = service.update(replyId, replyUpdateDto);
        return ResponseEntity.ok(reply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Reply> delete(@PathVariable Long replyId) {
        service.deleteById(replyId);
        return ResponseEntity.ok().build();
    }
}
