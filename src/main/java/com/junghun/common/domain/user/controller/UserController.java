package com.junghun.common.domain.user.controller;

import com.junghun.common.domain.user.dto.UserLoginDto;
import com.junghun.common.domain.user.dto.UserRegisterDto;
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
    public ResponseEntity<User> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        User registeredUser = userService.registerUser(userRegisterDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginDto userLoginDto) {
        User loggedInUser = userService.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
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
    public ResponseEntity<User> resetPassword(
            @RequestBody UserLoginDto userLoginDto) {
        User updatedUser = userService.resetPassword(userLoginDto.getEmail(), userLoginDto.getPassword());
        return ResponseEntity.ok(updatedUser);
    }


}