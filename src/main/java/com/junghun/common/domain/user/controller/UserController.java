package com.junghun.common.domain.user.controller;

import com.junghun.common.domain.user.dto.*;
import com.junghun.common.domain.user.model.User;
import com.junghun.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PutMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
        User registeredUser = service.register(registerDto);
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
        User updatedUser = service.updateCategory(userId, categoryList);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/profileImage")
    public ResponseEntity<User> updateProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile image) {
        User updatedUser = service.updateProfileImage(userId, image);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/location")
    public ResponseEntity<User> updateLocation(
            @PathVariable Long userId,
            @RequestBody Map<String, String> location) {
        User updatedUser = service.updateLocation(userId, location);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<User> updatePassword(
            @PathVariable Long userId,
            @RequestBody String password) {
        User updatedUser = service.updatePassword(userId, password);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/reset/password")
    public ResponseEntity<User> resetPassword(
            @RequestBody LoginDto loginDto) {
        User updatedUser = service.resetPassword(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/information")
    public ResponseEntity<User> updateUserInformation(
            @PathVariable Long userId,
            @RequestBody InformationDto informationDto) {
        User updatedUser = service.updateInformation(userId, informationDto);
        return ResponseEntity.ok(updatedUser);
    }

}