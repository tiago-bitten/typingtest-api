package com.labi.typing.DTO.score;

public record ScoreTopDTO(
        String username,
        Double wordsPerMinute,
        Double accuracy,
        Integer finishedTime,
        String testDate
) {
}
