package com.labi.typing.model;

import com.labi.typing.enums.TestDifficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_test;

    @Column(nullable = false)
    private LocalDate dateTest;

    @Column(nullable = false) @Length(max = 1000)
    private String testText;

    @Column(nullable = false)
    private Integer totalLetters;

    @Column(nullable = false)
    private Integer incorrectLetters;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestDifficulty testDifficulty;

    public Test(LocalDate dateTest, String testText, Integer totalLetters, Integer incorrectLetters, TestDifficulty testDifficulty) {
        this.dateTest = dateTest;
        this.testText = testText;
        this.totalLetters = totalLetters;
        this.incorrectLetters = incorrectLetters;
        this.testDifficulty = testDifficulty;
    }
}
