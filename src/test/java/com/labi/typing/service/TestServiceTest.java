package com.labi.typing.service;

import com.labi.typing.DTO.test.TestRegisterDTO;
import com.labi.typing.enums.TestDifficulty;
import com.labi.typing.enums.UserRole;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.repository.TestRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

class TestServiceTest {

    @InjectMocks
    private TestService testService;

    @Mock
    private TestRepository testRepository;

    @Mock
    private ScoreService scoreService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterTest_Success() {
        User user = new User("username", "email", "password");
        TestRegisterDTO dto = new TestRegisterDTO(LocalDateTime.now(), "testText",
                1, 1, 1, 1, TestDifficulty.SHORT);
        String authHeader = "authHeader";

        testService.registerTest(dto, authHeader);
    }

    @Test
    void testRegisterTest_Failure() {
        User user = new User("username", "email", "password");
        TestRegisterDTO dto = new TestRegisterDTO(LocalDateTime.now(), "testText",
                1, 1, 1, 1, TestDifficulty.SHORT);
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(null);

        ValidationException exception = new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        assert exception.getMessage().equals("User not found");
    }

    @Test
    void testGetShortTest_Success() {
        testService.getShortTest();
    }

    @Test
    void testGetMediumTest_Success() {
        testService.getMediumTest();
    }

    @Test
    void testGetLongTest_Success() {
        testService.getLongTest();
    }
}