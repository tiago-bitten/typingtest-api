package com.labi.typing.repository;

import com.labi.typing.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1")
    List<Score> findUserAllScore(String username);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'SHORT' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findUserTopShortScore(String username);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'MEDIUM' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findUserTopMediumScore(String username);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'LONG' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findUserTopLongScore(String username);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'SHORT' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findTopShortScore();

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'MEDIUM' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findTopMediumScore();

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'LONG' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    List<Score> findTopLongScore();
}
