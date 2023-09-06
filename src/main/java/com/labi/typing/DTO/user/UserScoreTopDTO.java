package com.labi.typing.DTO.user;

public record UserScoreTopDTO(
        String username,
        Double wordsPerMinute,
        Double accuracy,
        Integer finishedTime,
        String testDate
) {
}
