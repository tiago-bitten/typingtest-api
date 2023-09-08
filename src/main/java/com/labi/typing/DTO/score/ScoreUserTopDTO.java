package com.labi.typing.DTO.score;

import java.time.LocalDateTime;

public record ScoreUserTopDTO(
        Double wordsPerMinute,
        Double accuracy,
        Integer finishedTime,
        LocalDateTime testDate
) {
}
