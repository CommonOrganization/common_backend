package com.junghun.common.domain.daily.controller;

import com.junghun.common.domain.daily.dto.*;
import com.junghun.common.domain.daily.entity.Comments;
import com.junghun.common.domain.daily.entity.Daily;
import com.junghun.common.domain.daily.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{dailyId}/comments")
    public ResponseEntity<List<Comments>> findCommentsById(@PathVariable Long dailyId) {
        Daily daily = service.findById(dailyId);
        return ResponseEntity.ok(daily.getCommentList());
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
}
