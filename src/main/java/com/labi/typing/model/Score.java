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
    private Double wordsPerMinute;

    @Column(nullable = false)
    private Double accuracy;

    public Score(Double wordsPerMinute, Double accuracy) {
        this.wordsPerMinute = wordsPerMinute;
        this.accuracy = accuracy;
    }
}
