package com.junghun.common.domain.user.controller;

import com.junghun.common.domain.user.dto.*;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.service.UserPlaceService;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserPlaceService placeService;

    @PutMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterWrapper userRegisterWrapper) {
        User registeredUser = service.register(userRegisterWrapper.getRegisterDto());
        placeService.upload(registeredUser.getId(), userRegisterWrapper.getUserPlaceDto());
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        User loggedInUser = service.login(loginDto.getEmail(), loginDto.getPassword());
        if (loggedInUser != null) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{userId}/interestCategory")
    public ResponseEntity<User> updateInterestCategory(
            @PathVariable Long userId,
            @RequestBody List<String> categoryList) {
        service.updateCategory(userId, categoryList);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/userPlace")
    public ResponseEntity<User> updateUserPlace(
            @PathVariable Long userId,
            @RequestBody UserPlaceDto userPlaceDto) {
        placeService.update(userId, userPlaceDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<User> updatePassword(
            @PathVariable Long userId,
            @RequestBody String password) {
        User updatedUser = service.updatePassword(userId, password);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/reset/password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody LoginDto loginDto) {
        service.resetPassword(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/information")
    public ResponseEntity<User> updateUserInformation(
            @PathVariable Long userId,
            @RequestBody InformationDto informationDto) {
        User updatedUser = service.updateInformation(userId, informationDto);
        return ResponseEntity.ok(updatedUser);
    }

}