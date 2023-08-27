package com.labi.typing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_score;

    @Column(nullable = false)
    private Integer totalLetters;

    @Column(nullable = false)
    private Integer incorrectTypedLetters;

    @Column(nullable = false)
    private Double finishedTime;

    public Score(Integer totalLetters, Integer incorrectTypedLetters, Double finishedTime) {
        this.totalLetters = totalLetters;
        this.incorrectTypedLetters = incorrectTypedLetters;
        this.finishedTime = finishedTime;
    }
}
