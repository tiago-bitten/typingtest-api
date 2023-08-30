package com.labi.typing.DTO;

public record UserScoreTopDTO(
        String username,
        Double wordsPerMinute,
        Double accuracy,
        String testDate
) {
}
