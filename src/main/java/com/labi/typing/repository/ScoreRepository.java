package com.labi.typing.repository;

import com.labi.typing.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.id = ?1")
    List<Score> findUserScores(Long id);

    @Query("SELECT s FROM Score s JOIN s.test t ORDER BY s.wordsPerMinute DESC")
    List<Score> findAllScore();

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'SHORT' ORDER BY s.wordsPerMinute DESC")
    List<Score> findAllScoreShort();
}
