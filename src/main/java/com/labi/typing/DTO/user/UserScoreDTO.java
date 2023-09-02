package com.labi.typing.DTO.user;

public record UserScoreDTO(
        Double wordsPerMinute,
        Double accuracy
) {
}
