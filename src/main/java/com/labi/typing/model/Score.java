package com.labi.typing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_score")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_test")
    @JsonIgnore
    private Test test;

    public Score(Double wordsPerMinute, Double accuracy, Test test) {
        this.wordsPerMinute = wordsPerMinute;
        this.accuracy = accuracy;
        this.test = test;
    }
}
