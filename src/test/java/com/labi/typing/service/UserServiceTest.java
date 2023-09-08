package com.labi.typing.service;

import com.labi.typing.DTO.user.UserRegisterDTO;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.UserRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "email", "password");
        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(encoder.encode(dto.password())).thenReturn("encodedPassword");

        userService.registerUser(dto);
    }

    @Test
    void testRegisterUser_Failure_UsernameAlreadyExists() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "email", "password");
        when(userRepository.findByUsername(dto.username())).thenReturn(Optional.of(new User()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.registerUser(dto));
        assert exception.getMessage().equals("Username already exists");
    }

}