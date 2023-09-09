package com.labi.typing.service;

import com.labi.typing.enums.TestDifficulty;
import com.labi.typing.enums.UserRole;
import com.labi.typing.model.User;
import com.labi.typing.repository.ScoreRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterScore_Success() {
        User user = new User("username", "email", "password", UserRole.USER);
        com.labi.typing.model.Test test = new com.labi.typing.model.Test(LocalDateTime.now(), "testText",
                1, 1, 1, 1, TestDifficulty.SHORT, user);
        scoreService.registerScore(test);
    }

    @Test
    void testGetUserAllScore_Success() {
        User user = new User("username", "email", "password", UserRole.USER);
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);

        scoreService.getUserAllScore(authHeader);
    }

    @Test
    void testGetUserTopShortScore_Success() {
        User user = new User("username", "email", "password", UserRole.USER);
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);

        scoreService.getUserTopShortScore(authHeader);
    }

    @Test
    void testGetUserTopMediumScore_Success() {
        User user = new User("username", "email", "password", UserRole.USER);
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);

        scoreService.getUserTopMediumScore(authHeader);
    }

    @Test
    void testGetUserTopLongScore_Success() {
        User user = new User("username", "email", "password", UserRole.USER);
        String authHeader = "authHeader";

        when(jwtTokenProvider.getUserFromToken(authHeader, userService)).thenReturn(user);

        scoreService.getUserTopLongScore(authHeader);
    }
}