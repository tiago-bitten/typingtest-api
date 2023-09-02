package com.labi.typing.service;

import com.labi.typing.DTO.user.UserUpdatePasswordDTO;
import com.labi.typing.DTO.user.UserUpdateUsernameDTO;
import com.labi.typing.DTO.user.UserRegisterDTO;
import com.labi.typing.enums.UserRole;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void saveUser(UserRegisterDTO userRegisterDTO) {
        if (findByUsername(userRegisterDTO.username()) != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (findByEmail(userRegisterDTO.email()) != null) {
            throw new ValidationException("Email already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = mapUserRegisterDTOToUser(userRegisterDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUsername(UserUpdateUsernameDTO userUpdateUsernameDTO, String authHeader) {
        User existsUser = findByUsername(userUpdateUsernameDTO.username());
        if (existsUser != null) {
            throw new ValidationException("Username already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("Username not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setUsername(userUpdateUsernameDTO.username());
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordDTO dto, String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("Username not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!dto.password().equals(dto.confirmPassword())) {
            throw new ValidationException("Password doesnt match", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String newPassword = new BCryptPasswordEncoder().encode(dto.password());

        if (new BCryptPasswordEncoder().matches(user.getPassword(), newPassword)) {
            throw new ValidationException("Password cannot be the same", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        user.setPassword(newPassword);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        return new User(
                userRegisterDTO.username(),
                userRegisterDTO.email(),
                userRegisterDTO.password(),
                UserRole.USER
        );
    }
}
