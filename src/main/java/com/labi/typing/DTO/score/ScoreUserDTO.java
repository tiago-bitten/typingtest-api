package com.labi.typing.DTO.score;

import com.labi.typing.enums.TestDifficulty;

public record ScoreUserDTO(
        Double wordsPerMinute,
        Double accuracy,
        TestDifficulty testDifficulty,
        Integer finishedTime
) {
}
