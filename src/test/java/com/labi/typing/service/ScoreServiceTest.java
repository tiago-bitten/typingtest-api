package com.labi.typing.service;

import com.labi.typing.enums.TestDifficulty;
import com.labi.typing.repository.ScoreRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

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
        com.labi.typing.model.Test test = new com.labi.typing.model.Test(LocalDateTime.now(), "testText",
                1, 1, 1, 1, TestDifficulty.SHORT, null);
        scoreService.registerScore(test);
    }
}