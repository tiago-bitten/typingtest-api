package com.labi.typing.service;

import com.labi.typing.DTO.user.UserDeleteAccountDTO;
import com.labi.typing.DTO.user.UserRegisterDTO;
import com.labi.typing.DTO.user.UserResetPasswordDTO;
import com.labi.typing.DTO.user.UserUpdateUsernameDTO;
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

    @Test
    void testRegisterUser_Failure_EmailAlreadyExists() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "email", "password");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.registerUser(dto));
        assert exception.getMessage().equals("Email already exists");
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        UserDeleteAccountDTO dto = new UserDeleteAccountDTO("password", "password");
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);
        when(encoder.matches(dto.password(), user.getPassword())).thenReturn(true);

        userService.deleteUser(dto, authHeader);
    }

    @Test
    void testDeleteUser_Failure_PasswordsDontMatch() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("encodedPassword");

        UserDeleteAccountDTO dto = new UserDeleteAccountDTO("password", "password2");
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);
        when(encoder.matches(dto.password(), user.getPassword())).thenReturn(false);

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.deleteUser(dto, authHeader));
        assert exception.getMessage().equals("Passwords don't match");
    }

    @Test
    void testDeleteUser_Failure_DemoAccountCannotBeDeleted() {
        User user = new User();
        user.setUsername("demo");
        user.setPassword("encodedPassword");

        UserDeleteAccountDTO dto = new UserDeleteAccountDTO("password", "password");
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);
        when(encoder.matches(dto.password(), user.getPassword())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.deleteUser(dto, authHeader));
        assert exception.getMessage().equals("Demo account cannot be deleted");
    }

    @Test
    void testResetPassword_Success() {
        User user = new User();
        user.setUsername("username");
        user.setEmail("email");

        UserResetPasswordDTO dto = new UserResetPasswordDTO("email");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(user));

        userService.resetPassword(dto);
    }

    @Test
    void testResetPassword_Failure_EmailDoesntExists() {
        UserResetPasswordDTO dto = new UserResetPasswordDTO("email");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.resetPassword(dto));
        assert exception.getMessage().equals("Email doesn't exists");
    }

    @Test
    void testResetPassword_Failure_DemoAccountPasswordCannotBeReset() {
        User user = new User();
        user.setUsername("demo");
        user.setEmail("email");

        UserResetPasswordDTO dto = new UserResetPasswordDTO("email");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(user));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.resetPassword(dto));
        assert exception.getMessage().equals("Demo account password cannot be reset");
    }

    @Test
    void testUpdateUsername_Success() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("encodedPassword");
        UserUpdateUsernameDTO dto = new UserUpdateUsernameDTO("password", "newUsername");
        String authHeader = "authHeader";
        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);
        when(encoder.matches(dto.currentPassword(), user.getPassword())).thenReturn(true);

        userService.updateUsername(dto, authHeader);
    }

    @Test
    void testUpdateUsername_Failure_UsernameAlreadyExists() {
        User user = new User();
        user.setUsername("oldUsername");
        user.setPassword("encodedPassword");

        User existsUser = new User();
        existsUser.setUsername("newUsername");

        UserUpdateUsernameDTO dto = new UserUpdateUsernameDTO("password", "newUsername");
        String authHeader = "authHeader";
        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);
        when(encoder.matches(dto.currentPassword(), user.getPassword())).thenReturn(true);
        when(userRepository.findByUsername(dto.newUsername())).thenReturn(Optional.of(existsUser));

        ValidationException exception = assertThrows(ValidationException.class, () -> userService.updateUsername(dto, authHeader));
        assert exception.getMessage().equals("Username already exists");
    }
}