package com.labi.typing.DTO;

public record UserProfileDTO(
        String username,
        String password,
        String confirmPassword,
        String profileImgUrl
) {
}
