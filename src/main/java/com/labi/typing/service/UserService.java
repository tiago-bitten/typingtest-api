package com.labi.typing.service;

import com.labi.typing.DTO.user.*;
import com.labi.typing.enums.UserRole;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import com.labi.typing.util.ProfileImageUtil;
import com.labi.typing.util.ResetPasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    public void registerUser(UserRegisterDTO dto) {
        if (findByUsername(dto.username()) != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (findByEmail(dto.email()) != null) {
            throw new ValidationException("Email already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = mapUserRegisterDTOToUser(dto);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDeleteAccountDTO dto, String authHeader) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);
        if (!dto.password().equals(dto.confirmPassword())) {
            throw new ValidationException("Passwords don't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new ValidationException("Password doesn't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getUsername().equals("demo")) {
            throw new ValidationException("Demo account cannot be deleted", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        userRepository.delete(user);
    }

    @Transactional
    public void resetPassword(UserResetPasswordDTO dto) {
        User user = findByEmail(dto.email());
        if (user == null) {
            throw new ValidationException("Email doesn't exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getUsername().equals("demo")) {
            throw new ValidationException("Demo account password cannot be reset", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String newRandomPassword = ResetPasswordUtil.generateRandomPassword(6);
        emailService.emailResetPassword(user.getEmail(), newRandomPassword);
        user.setPassword(encoder.encode(newRandomPassword));
    }

    @Transactional
    public void updateUsername(UserUpdateUsernameDTO dto, String authHeader) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);
        if (!encoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new ValidationException("Password doesn't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User existsUser = findByUsername(dto.newUsername());
        if (existsUser != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getUsername().equals("demo")) {
            throw new ValidationException("Demo account username cannot be updated", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setUsername(dto.newUsername());
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordDTO dto, String authHeader) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);
        if (!encoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new ValidationException("Current Password doesn't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!dto.newPassword().equals(dto.confirmNewPassword())) {
            throw new ValidationException("Passwords don't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (encoder.matches(dto.newPassword(), user.getPassword())) {
            throw new ValidationException("Password cannot be the same", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getUsername().equals("demo")) {
            throw new ValidationException("Demo account password cannot be updated", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setPassword(encoder.encode(dto.newPassword()));
    }

    @Transactional
    public void uploadProfileImage(MultipartFile file, String authHeader) throws IOException {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);
        if (user.getUsername().equals("demo")) {
            throw new ValidationException("Demo account profile image cannot be updated", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getProfileImgUrl() != null) {
            ProfileImageUtil.delete(user.getProfileImgUrl());
        }

        String profileImage = ProfileImageUtil.upload(file);
        user.setProfileImgUrl(profileImage);
    }

    public byte[] getProfileImage(String authHeader) throws IOException {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);

        if (user.getProfileImgUrl() == null) {
            return null;
        }

        return ProfileImageUtil.recover(user.getProfileImgUrl());
    }

    public HttpHeaders getProfileImageMediaType(String authHeader) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);

        if (user.getProfileImgUrl() == null) {
            return null;
        }

        HttpHeaders header = new HttpHeaders();
        String extension = user.getProfileImgUrl().split("\\.")[1];

        switch (extension) {
            case "jpg":
            case "jpeg":
                header.setContentType(MediaType.IMAGE_JPEG);
                return header;
            case "png":
                header.setContentType(MediaType.IMAGE_PNG);
                return header;
            default:
                throw new ValidationException("Profile image extension not allowed", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public UserProfileDTO getProfile(String authHeader) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, this);
        return new UserProfileDTO(user.getUsername(), user.getEmail(), user.getProfileImgUrl(), user.getCreatedAt());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO dto) {
        return new User(dto.username(), dto.email(), dto.password(), UserRole.USER);
    }
}
