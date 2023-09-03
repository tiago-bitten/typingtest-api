package com.labi.typing.service;

import com.labi.typing.DTO.user.UserRegisterDTO;
import com.labi.typing.DTO.user.UserResetPasswordDTO;
import com.labi.typing.DTO.user.UserUpdatePasswordDTO;
import com.labi.typing.DTO.user.UserUpdateUsernameDTO;
import com.labi.typing.enums.UserRole;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import com.labi.typing.util.ResetPasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.labi.typing.util.ResetPasswordUtil.generateRandomPassword;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private EmailService emailService;

    public void saveUser(UserRegisterDTO dto) {
        if (findByUsername(dto.username()) != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (findByEmail(dto.email()) != null) {
            throw new ValidationException("Email already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = mapUserRegisterDTOToUser(dto);
        user.setPassword(bcrypt.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(UserResetPasswordDTO dto) {
        User user = findByEmail(dto.email());
        if (user == null) {
            throw new ValidationException("Email doenst exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String newRandomPassword = generateRandomPassword(6);
        emailService.emailResetPassword(user.getEmail(), newRandomPassword);
        user.setPassword(bcrypt.encode(newRandomPassword));
    }

    @Transactional
    public void updateUsername(UserUpdateUsernameDTO dto, String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("Username not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!bcrypt.matches(dto.currentPassword(), user.getPassword())) {
            throw new ValidationException("Password doesn't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User existsUser = findByUsername(dto.newUsername());
        if (existsUser != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setUsername(dto.newUsername());
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordDTO dto, String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("Username not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!bcrypt.matches(dto.currentPassword(), user.getPassword())) {
            throw new ValidationException("Current Password doesn't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!dto.newPassword().equals(dto.confirmNewPassword())) {
            throw new ValidationException("Passwords don't match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (bcrypt.matches(dto.newPassword(), user.getPassword())) {
            throw new ValidationException("Password cannot be the same", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setPassword(bcrypt.encode(dto.newPassword()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO dto) {
        return new User(
                dto.username(),
                dto.email(),
                dto.password(),
                UserRole.USER
        );
    }
}
