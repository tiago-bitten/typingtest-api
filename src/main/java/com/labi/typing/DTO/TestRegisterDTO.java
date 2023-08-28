package com.labi.typing.DTO;

import com.labi.typing.enums.TestDifficulty;
import com.labi.typing.model.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record TestRegisterDTO(
        LocalDateTime testDate,
        String testText,
        Integer totalWords,
        Integer finishedTime,
        Integer totalLetters,
        Integer incorrectLetters,
        TestDifficulty testDifficulty,
        UserDTO user
) {
}
