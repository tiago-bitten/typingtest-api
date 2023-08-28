package com.labi.typing.exception;

import java.time.Instant;
import java.util.Set;

public record ApiError(
        Instant timestamp,
        int status,
        String path,
        Set<Message> errors
) {
}
