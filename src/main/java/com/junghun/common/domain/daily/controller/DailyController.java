package com.junghun.common.domain.daily.controller;

import com.junghun.common.domain.daily.dto.CommentUpdateDto;
import com.junghun.common.domain.daily.dto.CommentUploadDto;
import com.junghun.common.domain.daily.dto.DailyUpdateDto;
import com.junghun.common.domain.daily.dto.DailyUploadDto;
import com.junghun.common.domain.daily.entity.Comment;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.repository.DailyRepository;
import com.junghun.common.domain.daily.service.DailyService;
import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.LoginDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService service;

    @PutMapping("/upload")
    public ResponseEntity<Daily> upload(@RequestBody DailyUploadDto dailyUploadDto) {
        Daily daily = service.upload(dailyUploadDto);
        return new ResponseEntity<>(daily, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Daily>> findAll() {
        List<Daily> dailyList = service.findAll();
        return ResponseEntity.ok(dailyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Daily> findById(@PathVariable Long id) {
        Daily daily = service.findById(id);
        return ResponseEntity.ok(daily);
    }

    @GetMapping("/writer/{writerId}")
    public ResponseEntity<List<Daily>> findByWriterId(@PathVariable Long writerId) {
        List<Daily> dailyList = service.findByWriterId(writerId);
        return ResponseEntity.ok(dailyList);
    }

    @GetMapping("/gathering/{clubGatheringId}")
    public ResponseEntity<List<Daily>> findByClubGatheringId(@PathVariable Long clubGatheringId) {
        List<Daily> dailyList = service.findByClubGatheringId(clubGatheringId);
        return ResponseEntity.ok(dailyList);
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<Daily>> findByKeyword(@PathVariable String keyword) {
        List<Daily> dailyList = service.findByKeyword(keyword);
        return ResponseEntity.ok(dailyList);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Daily>> findByCategory(@PathVariable String category) {
        List<Daily> dailyList = service.findByCategory(category);
        return ResponseEntity.ok(dailyList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Daily> update(@PathVariable Long id, @RequestBody DailyUpdateDto dailyUpdateDto) {
        Daily daily = service.update(id, dailyUpdateDto);
        return new ResponseEntity<>(daily, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{dailyId}/comment/upload")
    public ResponseEntity<Comment> upload(@PathVariable Long dailyId,@RequestBody CommentUploadDto commentUploadDto) {
        Comment comment = service.uploadComment(dailyId,commentUploadDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{dailyId}/comment")
    public ResponseEntity<List<Comment>> findAll(@PathVariable Long dailyId) {
        List<Comment> commentList = service.findAllComment(dailyId);
        return ResponseEntity.ok(commentList);
    }

    @PatchMapping("/{dailyId}/comment/{commentId}")
    public ResponseEntity<Comment> update(@PathVariable Long dailyId,@PathVariable Long commentId,@RequestBody CommentUpdateDto commentUpdateDto) {
        Comment comment = service.updateComment(dailyId,commentId,commentUpdateDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{dailyId}/comment/{commentId}")
    public ResponseEntity<Comment> delete(@PathVariable Long dailyId,@PathVariable Long commentId) {
        service.deleteCommentById(dailyId,commentId);
        return ResponseEntity.ok().build();
    }
}
