package com.labi.typing.service;

import com.labi.typing.model.Score;
import com.labi.typing.model.Test;
import com.labi.typing.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public void registerScore(Test test) {
        Score score = new Score();
        score.setAccuracy(calculateAccuracy(test.getTotalLetters(), test.getIncorrectLetters()));
        score.setWordsPerMinute(calculateWPM(test.getTotalWords(), test.getFinishedTime()));
        score.setTest(test);

        scoreRepository.save(score);
    }

    private Double calculateWPM(Integer words, Integer time) {
        double timeInMinutes = (double) time / 60;
        return words / timeInMinutes;
    }

    private Double calculateAccuracy(Integer letters, Integer incorrect) {
        return (double) (letters - incorrect) / letters * 100;
    }
}
