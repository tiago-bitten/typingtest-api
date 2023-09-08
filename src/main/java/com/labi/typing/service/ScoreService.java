package com.labi.typing.service;

import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.score.ScoreTopDTO;
import com.labi.typing.DTO.score.ScoreUserTopDTO;
import com.labi.typing.model.Score;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.ScoreRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.labi.typing.util.HeaderUtil.validateUserByHeader;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ScoreUserDTO registerScore(Test test) {
        double wpm = calculateWPM(test.getTotalWords(), test.getFinishedTime());
        double acc = calculateAccuracy(test.getTotalLetters(), test.getIncorrectLetters());
        Score score = new Score(wpm, acc, test);

        scoreRepository.save(score);

        return mapScoreToScoreUserDTO(score);
    }

    public List<ScoreUserDTO> getUserAllScore(String authHeader) {
        User user = validateUserByHeader(authHeader, userService, jwtTokenProvider);
        return scoreRepository.findUserAllScore(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserDTO)
                .toList();
    }

    public List<ScoreUserTopDTO> getUserTopShortScore(String authHeader) {
        User user = validateUserByHeader(authHeader, userService, jwtTokenProvider);
        return scoreRepository.findUserTopShortScore(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserTopDTO)
                .toList();
    }

    public List<ScoreUserTopDTO> getUserTopMediumScore(String authHeader) {
        User user = validateUserByHeader(authHeader, userService, jwtTokenProvider);
        return scoreRepository.findUserTopMediumScore(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserTopDTO)
                .toList();
    }

    public List<ScoreUserTopDTO> getUserTopLongScore(String authHeader) {
        User user = validateUserByHeader(authHeader, userService, jwtTokenProvider);
        return scoreRepository.findUserTopLongScore(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserTopDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopShortScore() {
        List<Score> scores = scoreRepository.findTopShortScore();
        return scores.stream()
                .map(this::mapScoreToScoreTopDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopMediumScore() {
        List<Score> scores = scoreRepository.findTopMediumScore();
        return scores.stream()
                .map(this::mapScoreToScoreTopDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopLongScore() {
        List<Score> scores = scoreRepository.findTopLongScore();
        return scores.stream()
                .map(this::mapScoreToScoreTopDTO)
                .toList();
    }

    private Double calculateWPM(Integer words, Integer time) {
        double timeInMinutes = (double) time / 60;
        double wpm = words / timeInMinutes;

        return Math.round(wpm * 100.0) / 100.0;
    }

    private Double calculateAccuracy(Integer letters, Integer incorrect) {
        double acc = ((double) (letters - incorrect)) / letters * 100;
        return Math.round(acc * 100.0) / 100.0;
    }

    private ScoreUserDTO mapScoreToScoreUserDTO(Score score) {
        return new ScoreUserDTO(
                score.getWordsPerMinute(),
                score.getAccuracy(),
                score.getTest().getTestDifficulty(),
                score.getTest().getFinishedTime(),
                score.getTest().getTestDate()
        );
    }

    private ScoreTopDTO mapScoreToScoreTopDTO(Score score) {
        return new ScoreTopDTO(
                score.getTest().getUser().getUsername(),
                score.getWordsPerMinute(),
                score.getAccuracy(),
                score.getTest().getFinishedTime(),
                score.getTest().getTestDate()
        );
    }

    private ScoreUserTopDTO mapScoreToScoreUserTopDTO(Score score) {
        return new ScoreUserTopDTO(
                score.getWordsPerMinute(),
                score.getAccuracy(),
                score.getTest().getFinishedTime(),
                score.getTest().getTestDate()
        );
    }
}