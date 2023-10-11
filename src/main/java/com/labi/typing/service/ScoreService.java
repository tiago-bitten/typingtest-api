package com.labi.typing.service;

import com.labi.typing.DTO.score.ScoreTopDTO;
import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.score.ScoreUserTopDTO;
import com.labi.typing.model.Score;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.ScoreRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ScoreUserDTO registerScore(Test test) {
        double wpm = calculateWPM(test.getTotalWords(), test.getInvalidWords(), test.getFinishedTime());
        double acc = calculateAccuracy(test.getTotalLetters(), test.getIncorrectLetters());
        Score score = new Score(wpm, acc, test);

        scoreRepository.save(score);

        return mapScoreToScoreUserDTO(score);
    }

    public Page<ScoreUserDTO> getUserAllScore(String authHeader, Pageable pageable) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, userService);
        Page<Score> scorePage = scoreRepository.findUserAllScore(user.getUsername(), pageable);

        return scorePage.map(this::mapScoreToScoreUserDTO);
    }


    public Page<ScoreUserTopDTO> getUserTopShortScore(String authHeader, Pageable pageable) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, userService);
        return scoreRepository.findUserTopShortScore(user.getUsername(), pageable)
                .map(this::mapScoreToScoreUserTopDTO);
    }

    public Page<ScoreUserTopDTO> getUserTopMediumScore(String authHeader, Pageable pageable) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, userService);
        return scoreRepository.findUserTopMediumScore(user.getUsername(), pageable)
                .map(this::mapScoreToScoreUserTopDTO);
    }

    public Page<ScoreUserTopDTO> getUserTopLongScore(String authHeader, Pageable pageable) {
        User user = jwtTokenProvider.getUserFromToken(authHeader, userService);
        return scoreRepository.findUserTopLongScore(user.getUsername(), pageable)
                .map(this::mapScoreToScoreUserTopDTO);
    }

    public Page<ScoreTopDTO> getTopShortScore(Pageable pageable) {
        return scoreRepository.findTopShortScore(pageable)
                .map(this::mapScoreToScoreTopDTO);
    }

    public Page<ScoreTopDTO> getTopMediumScore(Pageable pageable) {
        return scoreRepository.findTopMediumScore(pageable)
                .map(this::mapScoreToScoreTopDTO);
    }

    public Page<ScoreTopDTO> getTopLongScore(Pageable pageable) {
        return scoreRepository.findTopLongScore(pageable)
                .map(this::mapScoreToScoreTopDTO);
    }

    private Double calculateWPM(Integer words, Integer invalidWords, Integer time) {
        double timeInMinutes = (double) time / 60;
        double wpm = (words - invalidWords) / timeInMinutes;

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