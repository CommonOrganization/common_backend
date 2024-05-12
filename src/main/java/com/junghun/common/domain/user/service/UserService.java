package com.junghun.common.domain.user.service;

import com.junghun.common.domain.user.dto.InformationDto;
import com.junghun.common.domain.user.dto.RegisterDto;
import com.junghun.common.domain.user.entity.User;
import com.junghun.common.domain.user.exception.DuplicatedEmailException;
import com.junghun.common.domain.user.exception.NotFoundUserException;
import com.junghun.common.domain.user.repository.UserRepository;
import com.junghun.common.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    // READ
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email + "을(를) 가진 User 가 존재하지 않습니다."));
    }

    public InformationDto findInformationById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));
        InformationDto informationDto = new InformationDto();
        informationDto.setName(user.getName());
        informationDto.setInformation(user.getInformation());
        informationDto.setProfileImage(user.getProfileImage());
        return informationDto;
    }

    // CREATE
    public User register(RegisterDto registerDto) {

        // 만약 중복된 이메일이 저장되어있으면 가입불가 안내
        Optional<User> findEmail = repository.findByEmail(registerDto.getEmail());
        if (findEmail.isPresent()) {
            throw new DuplicatedEmailException(registerDto.getEmail() + "은 이미 가입된 이메일입니다.");
        }

        // 회원 정보 저장 전에 비밀번호를 암호화
        String encryptedPassword = passwordEncoder.encode(registerDto.getPassword());

        // userRegisterDto를 User 엔티티로 변환하여 저장
        User user = User.builder()
                .email(registerDto.getEmail())
                .name(registerDto.getName())
                .password(encryptedPassword)
                .gender(registerDto.getGender())
                .birthday(registerDto.getBirthday())
                .profileImage(registerDto.getProfileImage())
                .information(registerDto.getInformation())
                .categoryList(ConvertUtils.getStringByList(registerDto.getCategoryList()))
                .location(ConvertUtils.getStringByMap(registerDto.getLocation()))
                .build();

        return repository.save(user);
    }

    public User login(String email, String password) {

        User user = repository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email + "을(를) 가진 User 가 존재하지 않습니다."));
        // 사용자가 존재하고 비밀번호가 일치하면 사용자 반환
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        // 사용자가 없거나 비밀번호가 일치하지 않으면 null 반환
        return null;
    }

    // UPDATE
    public User resetPassword(String email, String newPassword) {

        String encryptedPassword = passwordEncoder.encode(newPassword);

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException(email + "을(를) 가진 User 가 존재하지 않습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(encryptedPassword)
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .location(ConvertUtils.getStringByMap(user.getLocation()))
                .categoryList(ConvertUtils.getStringByList(user.getCategoryList()))
                .profileImage(user.getProfileImage())
                .information(user.getInformation())
                .build();


        return repository.save(updateUser);
    }

    public User updatePassword(Long id, String newPassword) {

        String encryptedPassword = passwordEncoder.encode(newPassword);

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(encryptedPassword)
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .location(ConvertUtils.getStringByMap(user.getLocation()))
                .categoryList(ConvertUtils.getStringByList(user.getCategoryList()))
                .profileImage(user.getProfileImage())
                .information(user.getInformation())
                .build();

        return repository.save(updateUser);
    }

    public User updateCategory(Long id, List<String> categoryList) {

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .location(ConvertUtils.getStringByMap(user.getLocation()))
                .categoryList(ConvertUtils.getStringByList(categoryList))
                .profileImage(user.getProfileImage())
                .information(user.getInformation())
                .build();

        return repository.save(updateUser);
    }

    public User updateLocation(Long id, Map<String, String> location) {

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .location(ConvertUtils.getStringByMap(location))
                .categoryList(ConvertUtils.getStringByList(user.getCategoryList()))
                .profileImage(user.getProfileImage())
                .information(user.getInformation())
                .build();

        return repository.save(updateUser);
    }

    public User updateInformation(Long id, InformationDto informationDto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id + "을(를) 가진 User 가 존재하지 않습니다."));

        User updateUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(informationDto.getName())
                .password(user.getPassword())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .location(ConvertUtils.getStringByMap(user.getLocation()))
                .categoryList(ConvertUtils.getStringByList(user.getCategoryList()))
                .profileImage(informationDto.getProfileImage())
                .information(informationDto.getInformation())
                .build();

        return repository.save(updateUser);
    }

    // DELETE
    public void deleteById(Long userId) {
        repository.deleteById(userId);
    }
}