package com.labi.typing.service;

import com.labi.typing.DTO.test.TestRegisterDTO;
import com.labi.typing.enums.TestDifficulty;
import com.labi.typing.enums.UserRole;
import com.labi.typing.model.User;
import com.labi.typing.repository.TestRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

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
        User user = new User("username", "email", "password", UserRole.USER);
        TestRegisterDTO dto = new TestRegisterDTO(LocalDateTime.now(), "testText",
                1, 1, 1, 1, TestDifficulty.SHORT);
        String authHeader = "authHeader";

        testService.registerTest(dto, authHeader);
    }
}