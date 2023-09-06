package com.labi.typing.service;

import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.score.ScoreTopDTO;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.Score;
import com.labi.typing.model.Test;
import com.labi.typing.model.User;
import com.labi.typing.repository.ScoreRepository;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void registerScore(Test test) {
        double wpm = calculateWPM(test.getTotalWords(), test.getFinishedTime());
        double acc = calculateAccuracy(test.getTotalLetters(), test.getIncorrectLetters());
        Score score = new Score(wpm, acc, test);

        scoreRepository.save(score);
    }

    public List<ScoreUserDTO> getUserScores(String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return scoreRepository.findAllUserScores(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserDTO)
                .toList();
    }

    public List<ScoreUserDTO> getUserTopShort(String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return scoreRepository.findAllUserScoreShort(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserDTO)
                .toList();
    }

    public List<ScoreUserDTO> getUserTopMedium(String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return scoreRepository.findAllUserScoreMedium(user.getUsername()).stream()
                .map(this::mapScoreToScoreUserDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopScoresShort() {
        List<Score> scores = scoreRepository.findAllScoreShort();
        return scores.stream()
                .map(this::mapScoreToScoreTopDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopScoresMedium() {
        List<Score> scores = scoreRepository.findAllScoreMedium();
        return scores.stream()
                .map(this::mapScoreToScoreTopDTO)
                .toList();
    }

    public List<ScoreTopDTO> getTopScoresLong() {
        List<Score> scores = scoreRepository.findAllScoreLong();
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
                score.getTest().getFinishedTime()
        );
    }

    private ScoreTopDTO mapScoreToScoreTopDTO(Score score) {
        return new ScoreTopDTO(
                score.getTest().getUser().getUsername(),
                score.getWordsPerMinute(),
                score.getAccuracy(),
                score.getTest().getFinishedTime(),
                score.getTest().getTestDate().toString()
        );
    }
}
