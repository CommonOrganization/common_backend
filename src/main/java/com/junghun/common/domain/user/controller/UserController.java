package com.junghun.common.domain.user.controller;

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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
        User registeredUser = userService.register(registerDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
        User loggedInUser = userService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loggedInUser != null) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{userId}/notificationToken")
    public ResponseEntity<User> updateNotificationToken(
            @PathVariable Long userId,
            @RequestBody String newNotificationToken) {
        User updatedUser = userService.updateNotificationToken(userId, newNotificationToken);
        return ResponseEntity.ok(updatedUser);
    }


    @PatchMapping("/{userId}/interestCategory")
    public ResponseEntity<User> updateInterestCategory(
            @PathVariable Long userId,
            @RequestBody List<String> newInterestCategory) {
        User updatedUser = userService.updateInterestCategory(userId, newInterestCategory);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/userPlace")
    public ResponseEntity<User> updateUserPlace(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> newUserPlace) {
        User updatedUser = userService.updateUserPlace(userId, newUserPlace);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<User> updatePassword(
            @PathVariable Long userId,
            @RequestBody String password) {
        User updatedUser = userService.updatePassword(userId, password);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/reset/password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody LoginDto loginDto) {
        userService.resetPassword(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/information")
    public ResponseEntity<User> updateUserInformation(
            @PathVariable Long userId,
            @RequestBody InformationDto informationDto) {
        User updatedUser = userService.updateInformation(userId, informationDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/hello")
    public String go(){
        return "hello";
    }
}