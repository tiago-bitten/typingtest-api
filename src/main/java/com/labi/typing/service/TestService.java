package com.labi.typing.service;

import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.test.TestGeneratedDTO;
import com.labi.typing.DTO.test.TestRegisterDTO;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.TestRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.labi.typing.util.HeaderUtil.validateUserByHeader;
import static com.labi.typing.util.TestUtil.generateTest;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ScoreUserDTO saveTest(TestRegisterDTO dto, String authHeader) {
        User user = validateUserByHeader(authHeader, userService, jwtTokenProvider);
        Test test = mapTestRegisterDTOToTest(dto);
        test.setUser(user);

        testRepository.save(test);
        return scoreService.registerScore(test);
    }

    public TestGeneratedDTO getShortTest() {
        return new TestGeneratedDTO(generateTest(5, 3, 2));
    }

    public TestGeneratedDTO getMediumTest() {
        return new TestGeneratedDTO(generateTest(4, 7, 4));
    }

    public TestGeneratedDTO getLongTest() {
        return new TestGeneratedDTO(generateTest(4, 9, 7));
    }

    private Test mapTestRegisterDTOToTest(TestRegisterDTO dto) {
        return new Test(
                dto.testDate(),
                dto.testText(),
                dto.totalWords(),
                dto.finishedTime(),
                dto.totalLetters(),
                dto.incorrectLetters(),
                dto.testDifficulty()
        );
    }
}
