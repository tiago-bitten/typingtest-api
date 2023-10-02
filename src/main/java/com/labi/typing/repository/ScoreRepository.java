package com.labi.typing.repository;

import com.labi.typing.model.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1")
    Page<Score> findUserAllScore(String username, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'SHORT' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findUserTopShortScore(String username, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'MEDIUM' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findUserTopMediumScore(String username, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.user.username = ?1 AND t.testDifficulty = 'LONG' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findUserTopLongScore(String username, Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'SHORT' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findTopShortScore(Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'MEDIUM' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findTopMediumScore(Pageable pageable);

    @Query("SELECT s FROM Score s JOIN s.test t WHERE t.testDifficulty = 'LONG' ORDER BY s.wordsPerMinute - t.finishedTime DESC")
    Page<Score> findTopLongScore(Pageable pageable);
}
