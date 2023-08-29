package com.labi.typing.DTO;

public record UserScoreDTO(
        Double wordsPerMinute,
        Double accuracy
) {
}
