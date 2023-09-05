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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_score")
public class Score extends EntityId {

    @Column(nullable = false)
    private Double wordsPerMinute;

    @Column(nullable = false)
    private Double accuracy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test")
    @JsonIgnore
    private Test test;
}
