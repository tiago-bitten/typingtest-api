package com.labi.typing.DTO;

import com.labi.typing.enums.TestDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TestRegisterDTO(
        @NotNull(message = "Test date cannot be null")
        LocalDateTime testDate,
        @NotBlank(message = "Test text cannot be blank")
        String testText,
        @NotNull(message = "Total words cannot be null")
        Integer totalWords,
        @NotNull(message = "Finished time cannot be null")
        Integer finishedTime,
        @NotNull(message = "Total letters cannot be null")
        Integer totalLetters,
        @NotNull(message = "Incorrect letters cannot be null")
        Integer incorrectLetters,
        @NotNull(message = "Test difficulty cannot be null")
        TestDifficulty testDifficulty
) {
}
