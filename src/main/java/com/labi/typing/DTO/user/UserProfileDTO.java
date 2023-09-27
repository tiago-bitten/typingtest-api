package com.labi.typing.DTO.user;

import java.time.Instant;

public record UserProfileDTO(
        String username,
        String email,
        String profileImage,
        Instant createdAt
) {
}
