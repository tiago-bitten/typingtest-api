package com.labi.typing.DTO.user;

import com.labi.typing.enums.TestDifficulty;

public record UserScoreDTO(
        Double wordsPerMinute,
        Double accuracy,
        TestDifficulty testDifficulty,
        Integer finishedTime
) {
}
