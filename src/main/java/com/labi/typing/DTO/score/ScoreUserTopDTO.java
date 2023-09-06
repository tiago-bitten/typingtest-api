package com.labi.typing.DTO.score;

public record ScoreUserTopDTO(
        Double wordsPerMinute,
        Double accuracy,
        Integer finishedTime,
        String testDate
) {
}
