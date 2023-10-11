package com.labi.typing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.labi.typing.enums.TestDifficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_test")
public class Test extends EntityId {

    @Column(nullable = false)
    private LocalDateTime testDate;

    @Column(nullable = false)
    private String testText;

    @Column(nullable = false)
    private Integer totalWords;

    @Column(nullable = false)
    private Integer invalidWords;

    @Column(nullable = false)
    private Integer finishedTime;

    @Column(nullable = false)
    private Integer totalLetters;

    @Column(nullable = false)
    private Integer incorrectLetters;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestDifficulty testDifficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Score score;

    public Test(LocalDateTime testDate, String testText, Integer totalWords, Integer invalidWords, Integer finishedTime,
                Integer totalLetters, Integer incorrectLetters, TestDifficulty testDifficulty, User user) {
        this.testDate = testDate;
        this.testText = testText;
        this.totalWords = totalWords;
        this.invalidWords = invalidWords;
        this.finishedTime = finishedTime;
        this.totalLetters = totalLetters;
        this.incorrectLetters = incorrectLetters;
        this.testDifficulty = testDifficulty;
        this.user = user;
    }
}
