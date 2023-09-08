package com.labi.typing.DTO.score;

import java.time.LocalDateTime;

public record ScoreTopDTO(
        String username,
        Double wordsPerMinute,
        Double accuracy,
        Integer finishedTime,
        LocalDateTime testDate
) {
}
